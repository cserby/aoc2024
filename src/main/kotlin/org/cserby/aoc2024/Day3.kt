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
}
