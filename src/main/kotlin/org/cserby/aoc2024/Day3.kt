package org.cserby.aoc2024

object Day3 {
    private val mulRegex = Regex("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)")

    private fun findMuls(line: String): List<Pair<Int, Int>> {
        return mulRegex.findAll(line).map { matchResult ->
            matchResult.groupValues[1].toInt() to matchResult.groupValues[2].toInt()
        }.toList()
    }

    fun part1(input: String): Int {
        return input.lines()
            .flatMap(Day3::findMuls)
            .fold(0) { acc, pair -> acc + pair.first * pair.second }
    }

    private fun findMulsWithDo(line: String): List<Pair<Int, Int>> {
        tailrec fun findMulsWithDoRec(line: String, enabled: Boolean, acc: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
            if (line == "") return acc
            if (enabled) {
                val split = line.split("don't\\(\\)".toRegex(), 2)
                if (split.size != 2) return acc + findMuls(split[0])
                val (doPart, rest) = split
                return findMulsWithDoRec(rest, false, findMuls(doPart) + acc)
            } else {
                val split = line.split("do\\(\\)".toRegex(), 2)
                if (split.size != 2) return acc
                val (_, rest) = split
                return findMulsWithDoRec(rest, true, acc)
            }
        }

        return findMulsWithDoRec(line, true, emptyList())
    }

    fun part2(input: String): Int {
        return findMulsWithDo(input.lines().joinToString(""))
            .fold(0) { acc, pair -> acc + pair.first * pair.second }
    }
}
