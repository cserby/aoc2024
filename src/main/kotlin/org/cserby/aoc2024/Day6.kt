package org.cserby.aoc2024

import org.cserby.aoc2024.Direction.DOWN
import org.cserby.aoc2024.Direction.LEFT
import org.cserby.aoc2024.Direction.RIGHT
import org.cserby.aoc2024.Direction.UP
import kotlin.jvm.Throws

enum class Field(fld: Char) {
    EMPTY('.'),
    OBSTACLE('#');

    companion object {
        fun fromChar(dir: Char): Field {
            return when(dir) {
                '.' -> EMPTY
                '#' -> OBSTACLE
                else -> throw IllegalArgumentException("'$dir' is not a Field")
            }
        }
    }
}

typealias Maze = List<List<Field>>
typealias Position = Pair<Int, Int>

enum class Direction(dir: Char) {
    UP('^'),
    RIGHT('>'),
    DOWN('v'),
    LEFT('<');

    companion object {
        fun fromChar(dir: Char): Direction {
            return when(dir) {
                '^' -> UP
                '>' -> RIGHT
                'v' -> DOWN
                '<' -> LEFT
                else -> throw IllegalArgumentException("'$dir' is not a Direction")
            }
        }
    }
}

data class GuardPosition(val position: Position, val direction: Direction) {
    fun step(): GuardPosition {
        return when(direction) {
            UP -> copy(position = position.copy(first = position.first - 1))
            RIGHT -> copy(position = position.copy(second = position.second + 1))
            DOWN -> copy(position = position.copy(first = position.first + 1))
            LEFT -> copy(position = position.copy(second = position.second - 1))
        }
    }

    fun turnRight(): GuardPosition {
        return copy(direction = when(direction) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        })
    }
}

data class Situation(val maze: Maze, val guardPosition: GuardPosition, val visited: Set<Position>)

fun Maze.fieldAt(position: Position): Field {
    return get(position.first).get(position.second)
}

@Throws(IndexOutOfBoundsException::class)
fun Situation.next(): Situation {
    val nextGuardPosition = guardPosition.step()
    if (maze.fieldAt(nextGuardPosition.position) == Field.OBSTACLE) {
        return copy(guardPosition = guardPosition.turnRight()).next()
    }
    return copy(guardPosition = nextGuardPosition, visited = visited + nextGuardPosition.position)
}

object Day6 {
    private fun parse(input: String): Situation {
        var position: GuardPosition? = null
        val maze = input.lines()
            .mapIndexed { x, line ->
                line.toCharArray()
                    .mapIndexed { y, fld ->
                        runCatching { Field.fromChar(fld) }.recoverCatching { e ->
                            when {
                                e is IllegalArgumentException -> {
                                    position = GuardPosition(x to y, Direction.fromChar(fld))
                                    return@recoverCatching Field.EMPTY
                                }
                                else -> throw e
                            }
                        }.getOrThrow()
                    }
            }
        return Situation(maze, position!!, setOf(position.position))
    }

    private fun steps(start: Situation): Sequence<Situation> = sequence {
        var situation = start
        while (true) {
            try {
                situation = situation.next()
                yield(situation)
            } catch (_: IndexOutOfBoundsException) {
                return@sequence
            }
        }
    }

    fun part1(input: String): Int {
        val start = parse(input)
        return steps(start).last().visited.size
    }
}