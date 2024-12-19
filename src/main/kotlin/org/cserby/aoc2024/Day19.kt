package org.cserby.aoc2024

object Day19 {
    private fun parse(input: String): Pair<List<String>, List<String>> {
        val lines = input.lines()
        val towels = lines[0].split(", ")

        val designs = lines.drop(2)

        return designs to towels
    }

    private fun possible(
        design: String,
        towels: List<String>,
    ): Boolean {
        if (design.isEmpty()) return true
        for (towel in towels) {
            if (design.startsWith(towel) && possible(design.substringAfter(towel), towels)) return true
        }
        return false
    }

    fun part1(input: String): Int {
        val (designs, towels) = parse(input)

        return designs.count { possible(it, towels) }
    }
}
