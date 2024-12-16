package org.cserby.aoc2024

import org.cserby.aoc2024.Utils.Direction
import org.cserby.aoc2024.day12.cell
import org.cserby.aoc2024.day12.copy

object Day16 {
    enum class ReindeerMazeField(val chr: Char) {
        EMPTY('.'),
        WALL('#'),
        END('E'),
        START('S'),
    }

    data class ReindeerState(val pos: Pair<Int, Int>, val heading: Direction, val costSoFar: Int) {
        fun turnRight(): ReindeerState {
            val nextHeading =
                when (heading) {
                    Direction.UP -> Direction.RIGHT
                    Direction.RIGHT -> Direction.DOWN
                    Direction.DOWN -> Direction.LEFT
                    Direction.LEFT -> Direction.UP
                }
            return copy(
                pos = neighborPoint(pos, nextHeading),
                heading = nextHeading,
                costSoFar = costSoFar + 1000 + 1,
            )
        }

        fun turnLeft(): ReindeerState {
            val nextHeading =
                when (heading) {
                    Direction.UP -> Direction.LEFT
                    Direction.RIGHT -> Direction.UP
                    Direction.DOWN -> Direction.RIGHT
                    Direction.LEFT -> Direction.DOWN
                }
            return copy(
                pos = neighborPoint(pos, nextHeading),
                heading = nextHeading,
                costSoFar = costSoFar + 1000 + 1,
            )
        }

        private fun neighborPoint(
            point: Pair<Int, Int>,
            direction: Direction,
        ): Pair<Int, Int> {
            return when (direction) {
                Direction.UP -> point.copy(first = point.first - 1)
                Direction.DOWN -> point.copy(first = point.first + 1)
                Direction.LEFT -> point.copy(second = point.second - 1)
                Direction.RIGHT -> point.copy(second = point.second + 1)
            }
        }

        fun moveForward(): ReindeerState {
            return copy(pos = neighborPoint(pos, heading), costSoFar = costSoFar + 1)
        }
    }

    data class ReindeerMaze(
        val fields: List<List<ReindeerMazeField>>,
        val reindeerStartPosition: Pair<Int, Int>,
        val endPosition: Pair<Int, Int>,
    ) {
        fun lowestCostReindeerPath(): Int {
            var frontier: Set<ReindeerState> =
                setOf(
                    ReindeerState(pos = reindeerStartPosition, heading = Direction.RIGHT, costSoFar = 0),
                )
            var lowestCosts: MutableList<MutableList<Int?>> = fields.copy { null }

            do {
                val currReindeerState = frontier.take(1)[0]
                frontier = frontier.minus(currReindeerState)

                listOf(
                    currReindeerState.moveForward(),
                    currReindeerState.turnLeft(),
                    currReindeerState.turnRight(),
                ).forEach { nextReindeerState ->
                    if (fields.cell(nextReindeerState.pos) != ReindeerMazeField.WALL) {
                        if ((
                                lowestCosts.cell(nextReindeerState.pos)
                                    ?: Integer.MAX_VALUE
                            ) > nextReindeerState.costSoFar
                        ) {
                            lowestCosts[nextReindeerState.pos.first][nextReindeerState.pos.second] =
                                nextReindeerState.costSoFar
                            frontier = frontier.plus(nextReindeerState)
                        }
                    }
                }
            } while (frontier.isNotEmpty())

            return lowestCosts[endPosition.first][endPosition.second]!!
        }

        fun display(reindeerPosition: Pair<Int, Int>): String {
            return fields.indices.map { x ->
                fields[x].indices.map { y ->
                    if (reindeerPosition == x to y) {
                        '@'
                    } else {
                        fields[x][y].chr
                    }
                }.joinToString("")
            }.joinToString("\n")
        }

        companion object {
            fun parse(input: String): ReindeerMaze {
                var startPos: Pair<Int, Int>? = null
                var endPos: Pair<Int, Int>? = null

                val mazeFields =
                    input.lines().mapIndexed { x, line ->
                        line.toCharArray().mapIndexed { y, field ->
                            when (field) {
                                '#' -> ReindeerMazeField.WALL
                                'S' -> {
                                    startPos = x to y
                                    ReindeerMazeField.START
                                }
                                'E' -> {
                                    endPos = x to y
                                    ReindeerMazeField.END
                                }

                                '.' -> ReindeerMazeField.EMPTY
                                else -> throw Exception("Unknown warehouse field at ($x, $y): $field")
                            }
                        }
                    }
                return ReindeerMaze(fields = mazeFields, reindeerStartPosition = startPos!!, endPosition = endPos!!)
            }
        }
    }

    fun part1(input: String): Int {
        return ReindeerMaze.parse(input).lowestCostReindeerPath()
    }

    fun part2(input: String): Int {
        return 9
    }
}
