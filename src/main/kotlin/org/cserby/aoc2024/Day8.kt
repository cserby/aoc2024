package org.cserby.aoc2024

typealias Point = Pair<Int, Int>
typealias Antennae = Map<Char, List<Point>>

fun <E> List<E>.pairs(): List<Pair<E, E>> {
    return mapIndexed { index, a ->
        slice(index + 1 until size).map { Pair(a, it) }
    }.flatten()
}

object Day8 {
    private fun parse(input: String): Antennae {
        return input.lines().flatMapIndexed { x, line ->
            line.mapIndexed { y, field ->
                if (field != '.' && field != '#') return@mapIndexed field to (x to y)
                return@mapIndexed null
            }
        }.filterNotNull().groupBy({ it.first }, { it.second })
    }

    fun interference(
        antenna1: Point,
        antenna2: Point,
    ): Set<Point> {
        val (x1, y1) = antenna1
        val (x2, y2) = antenna2

        return setOf(
            (x1 - (x2 - x1)) to (y1 - (y2 - y1)),
            (x2 - (x1 - x2)) to (y2 - (y1 - y2)),
        )
    }

    fun part1(input: String): Int {
        val lineCount = input.lines().size
        val colCount = input.lines()[0].toCharArray().size

        val antinodes =
            parse(input).flatMap { (_, antennaList) ->
                antennaList.pairs().fold(emptySet<Point>()) { prev, (a1, a2) ->
                    prev + interference(a1, a2)
                }
            }.fold(emptySet<Point>()) { prev, curr -> prev + curr }
                .filter { (x, y) ->
                    x >= 0 && x < lineCount &&
                        y >= 0 && y < colCount
                }
        return antinodes
            .count()
    }

    fun part2(input: String): Int {
        return 53
    }
}
