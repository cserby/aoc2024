package org.cserby.aoc2024

import org.cserby.aoc2024.day12.cell
import org.cserby.aoc2024.day12.neighbors
import kotlin.math.abs

object Day20 {
    enum class ProgramMazeField(val chr: Char) {
        EMPTY('.'),
        WALL('#'),
    }

    data class FieldCost(
        val field: Pair<Int, Int>,
        val cost: Int,
    )

    data class ProgramMaze(
        val fields: List<List<ProgramMazeField>>,
        val start: Pair<Int, Int>,
        val end: Pair<Int, Int>,
    ) {
        private val fieldCosts: MutableList<MutableList<Pair<Int, Pair<Int, Int>?>>>

        init {
            fieldCosts =
                fields.map { it.map { Int.MAX_VALUE to null as Pair<Int, Int>? }.toMutableList() }.toMutableList()

            var frontier = setOf(start)
            fieldCosts[start.first][start.second] = 0 to null

            while (frontier.isNotEmpty()) {
                val curr = frontier.take(1)[0]
                frontier = frontier.minus(curr)

                val currCost = fieldCosts.cell(curr).first

                val neighborsToCheck =
                    frontier.plus(
                        fields.neighbors(curr)
                            .filterNot { fields.cell(it) == ProgramMazeField.WALL }
                            .filter { currCost + 1 < fieldCosts.cell(it).first },
                    )
                neighborsToCheck.forEach {
                    fieldCosts[it.first][it.second] = currCost + 1 to curr
                }

                frontier = frontier.plus(neighborsToCheck)
            }
        }

        fun shortestPathWithoutCheating(): List<FieldCost> {
            val shortestPath = emptyList<FieldCost>().toMutableList()

            var curr = end
            do {
                val (currCost, currPrev) = fieldCosts.cell(curr)
                if (currPrev == null) break
                shortestPath.add(FieldCost(currPrev, currCost))
                curr = currPrev
            } while (true)

            return shortestPath.reversed()
        }

        companion object {
            fun parse(input: String): ProgramMaze {
                var start: Pair<Int, Int>? = null
                var end: Pair<Int, Int>? = null

                val fields =
                    input.lines().mapIndexed { x, line ->
                        line.toCharArray().mapIndexed { y, field ->
                            when (field) {
                                '.' -> ProgramMazeField.EMPTY
                                '#' -> ProgramMazeField.WALL
                                'E' -> {
                                    end = x to y
                                    ProgramMazeField.EMPTY
                                }
                                'S' -> {
                                    start = x to y
                                    ProgramMazeField.EMPTY
                                }
                                else -> throw Exception("Unknown field: $field")
                            }
                        }
                    }

                return ProgramMaze(fields, start!!, end!!)
            }
        }
    }

    private fun cheatAt(
        pos: Pair<Int, Int>,
        path: List<FieldCost>,
    ): List<Int> {
        val pathPoints = path.map { it.field to it.cost }.toMap()

        val possibleCheatEnds =
            listOf(0 to 2, 0 to -2, 2 to 0, -2 to 0)
                .map { pos.first + it.first to pos.second + it.second }
                .filter { pathPoints.keys.contains(it) }

        val possibleCheatsSavings =
            possibleCheatEnds
                .map { pathPoints[it]!! - pathPoints[pos]!! - 2 }
                .filter { it > 0 }

        return possibleCheatsSavings
    }

    fun part1(input: String): Int {
        val maze = ProgramMaze.parse(input)
        val shortestPathWithoutCheating = maze.shortestPathWithoutCheating()
        val possibleCheats = shortestPathWithoutCheating.map { it.field to cheatAt(it.field, shortestPathWithoutCheating) }
        return possibleCheats.flatMap { it.second }.count { it >= 100 }
    }

    fun godMode(
        pos: Pair<Int, Int>,
        pathPoints: Map<Pair<Int, Int>, Int>,
    ): List<Pair<Pair<Int, Int>, Int>> {
        val possibleCheats =
            pathPoints.keys.map {
                val distance = abs(it.first - pos.first) + abs(it.second - pos.second)
                it to distance
            }.filter { (_, distance) ->
                distance <= 20
            }.map { (possibleCheatEnd, distance) ->
                val costSavings = pathPoints[possibleCheatEnd]!! - pathPoints[pos]!! - distance
                possibleCheatEnd to costSavings
            }.filter { (_, costSavings) ->
                costSavings > 0
            }

        return possibleCheats
    }

    fun part2(input: String): Int {
        val maze = ProgramMaze.parse(input)
        val shortestPathWithoutCheating = maze.shortestPathWithoutCheating()
        val pathPoints =
            shortestPathWithoutCheating.map { it.field to it.cost }.toMap().plus(
                maze.end to shortestPathWithoutCheating.size + 1,
            )
        val possibleCheats = shortestPathWithoutCheating.map { it.field to godMode(it.field, pathPoints) }
        return possibleCheats.map { it.second }.flatMap { it }.count { it.second >= 100 }
    }
}
