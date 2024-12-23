package org.cserby.aoc2024

typealias Lan = Map<String, Set<String>>

object Day23 {
    fun parse(input: String): Lan {
        return input.lines().flatMap { line ->
            val parts = line.split("-")
            listOf(parts[0] to parts[1], parts[1] to parts[0])
        }.groupingBy { it.first }
            .aggregate { from, accumulator, (_from, to), first ->
                if (first) setOf(to) else accumulator!!.plus(to)
            }
    }

    fun tripletsWithT(lan: Lan): Set<Set<String>> {
        return lan.flatMap { (first, seconds) ->
            seconds.flatMap { second ->
                lan[second]!!.map { third ->
                    if (lan[third]!!.contains(first)) setOf(first, second, third) else null
                }
            }
        }
            .filterNotNull()
            .filter { it.size == 3 }
            .filter { it.any { host -> host.startsWith("t") } }
            .toSet()
    }

    // https://en.wikipedia.org/wiki/Bron%E2%80%93Kerbosch_algorithm
    fun bronKerbosch(
        lan: Lan,
        clique: Set<String>,
        someOfArg: Set<String>,
        noneOfArg: Set<String>,
    ): Sequence<Set<String>> =
        sequence {
            if (someOfArg.isEmpty() && noneOfArg.isEmpty()) yield(clique)

            val someOf = someOfArg.toMutableSet()
            val noneOf = noneOfArg.toMutableSet()

            while (someOf.isNotEmpty()) {
                val v = someOf.take(1)[0]
                yieldAll(bronKerbosch(lan, clique.plus(v), someOf.intersect(lan[v]!!), noneOf.intersect(lan[v]!!)))
                someOf.remove(v)
                noneOf.add(v)
            }
        }

    fun part1(input: String): Int {
        return tripletsWithT(parse(input)).size
    }

    fun part2(input: String): String {
        val lan = parse(input)
        val cliques = bronKerbosch(lan, emptySet<String>(), lan.keys, emptySet<String>())
        return cliques.sortedBy { it.size }.last().sorted().joinToString(",")
    }
}
