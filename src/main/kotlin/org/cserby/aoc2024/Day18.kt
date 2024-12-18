package org.cserby.aoc2024

import org.cserby.aoc2024.day12.cell
import org.cserby.aoc2024.day12.neighbors

object Day18 {
    private fun parse(
        input: String,
        corruptedBytes: Int? = null,
    ): Set<Pair<Int, Int>> {
        return (if (corruptedBytes != null) input.lines().take(corruptedBytes) else input.lines())
            .map { it.split(",").let { (x, y) -> y.toInt() to x.toInt() } }.toSet()
    }

    fun shortestPathToEnd(
        memorySpaceEnd: Int,
        blocks: Set<Pair<Int, Int>>,
    ): Int {
        val shortestPathToEnd: MutableList<MutableList<Int>> =
            (0..memorySpaceEnd).map { x ->
                (0..memorySpaceEnd).map { y ->
                    Int.MAX_VALUE
                }.toMutableList()
            }.toMutableList()

        var frontier = setOf(0 to 0)
        shortestPathToEnd[0][0] = 0

        while (frontier.isNotEmpty()) {
            val currPos = frontier.take(1)[0]
            frontier = frontier.minus(currPos)
            val currPathLength = shortestPathToEnd.cell(currPos)

            shortestPathToEnd
                .neighbors(currPos)
                .filterNot {
                    blocks.contains(it)
                }
                .forEach {
                    if (currPathLength + 1 < shortestPathToEnd.cell(it)) {
                        shortestPathToEnd[it.first][it.second] = currPathLength + 1
                        frontier = frontier.plus(it)
                    }
                }
        }

        return shortestPathToEnd[memorySpaceEnd][memorySpaceEnd]
    }

    fun part1(
        input: String,
        memorySpaceEnd: Int = 70,
        corruptedBytes: Int = 1024,
    ): Int {
        val blocks = parse(input, corruptedBytes)
        return shortestPathToEnd(memorySpaceEnd, blocks)
    }

    fun part2(
        input: String,
        memorySpaceEnd: Int = 70,
    ): String {
        val inputLines = input.lines()
        val maxCorruption = inputLines.size
        val corruptionIndex =
            (0..<maxCorruption).indexOfFirst { corruptedBytes ->
                val blocks = parse(input, corruptedBytes)

                shortestPathToEnd(memorySpaceEnd, blocks) == Int.MAX_VALUE
            }
        return inputLines[corruptionIndex - 1]
    }
}
