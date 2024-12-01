package org.cserby.aoc2024;

object Day1 {
    private fun parse(input: String): Pair<List<Int>, List<Int>> {
        val pairList = input.lines()
            .map{ parseLine(it) }
        return pairList.map { it.first } to pairList.map { it.second }
    }

    private fun parseLine(line: String): Pair<Int, Int> {
        val split = line.split("\\s+".toRegex(), 2)
        return Pair(split[0].toInt(), split[1].toInt())
    }

    fun part1(input: String): Int {
        val lists = parse(input)
        val firstList = lists.first.sorted()
        val secondList = lists.second.sorted()
        val pairs = firstList.zip(secondList)
        val distances = pairs.map{ (first, second) -> kotlin.math.abs(first - second) }
        return distances.sum()
    }
}
