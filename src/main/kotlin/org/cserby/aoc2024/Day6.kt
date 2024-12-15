package org.cserby.aoc2024

import org.cserby.aoc2024.Utils.Direction

enum class Field(fld: Char) {
    EMPTY('.'),
    OBSTACLE('#'),
    ;

    companion object {
        fun fromChar(dir: Char): Field {
            return when (dir) {
                '.' -> EMPTY
                '#' -> OBSTACLE
                else -> throw IllegalArgumentException("'$dir' is not a Field")
            }
        }
    }
}

typealias Maze = List<List<Field>>
typealias Position = Pair<Int, Int>

data class GuardPosition(val position: Position, val direction: Direction) {
    fun step(): GuardPosition {
        return when (direction) {
            Direction.UP -> copy(position = position.copy(first = position.first - 1))
            Direction.RIGHT -> copy(position = position.copy(second = position.second + 1))
            Direction.DOWN -> copy(position = position.copy(first = position.first + 1))
            Direction.LEFT -> copy(position = position.copy(second = position.second - 1))
        }
    }

    fun turnRight(): GuardPosition {
        return copy(
            direction =
                when (direction) {
                    Direction.UP -> Direction.RIGHT
                    Direction.RIGHT -> Direction.DOWN
                    Direction.DOWN -> Direction.LEFT
                    Direction.LEFT -> Direction.UP
                },
        )
    }
}

data class GuardState(val guardPosition: GuardPosition, val visited: Set<GuardPosition>)

fun Maze.fieldAt(position: Position): Field {
    return get(position.first)[position.second]
}

class BeenHereException(guardState: GuardState, nextGuardPosition: GuardPosition) :
    Exception("Already been at $nextGuardPosition in $guardState")

tailrec fun GuardState.next(maze: Maze): GuardState {
    val nextGuardPosition = guardPosition.step()

    if (visited.contains(nextGuardPosition)) throw BeenHereException(this, nextGuardPosition)

    if (maze.fieldAt(nextGuardPosition.position) == Field.OBSTACLE) {
        val positionAfterTurn = guardPosition.turnRight()
        return copy(guardPosition = positionAfterTurn, visited = visited + positionAfterTurn).next(maze)
    }

    return copy(guardPosition = nextGuardPosition, visited = visited + nextGuardPosition)
}

class AlreadyBlockException(position: Position) : Exception("$position is already a block")

fun Maze.addBlock(position: Position): Maze {
    return mapIndexed { x, line ->
        if (x != position.first) {
            line
        } else {
            line.mapIndexed { y, fld ->
                if (y != position.second) {
                    fld
                } else {
                    Field.OBSTACLE
                }
            }
        }
    }
}

fun GuardState.steps(maze: Maze): Sequence<GuardState> =
    sequence {
        var guardState = this@steps
        while (true) {
            try {
                guardState = guardState.next(maze)
                yield(guardState)
            } catch (_: IndexOutOfBoundsException) {
                return@sequence
            }
        }
    }

object Day6 {
    private fun parse(input: String): Pair<Maze, GuardState> {
        var position: GuardPosition? = null
        val maze =
            input.lines()
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
        return maze to GuardState(position!!, setOf(position))
    }

    fun part1(input: String): Int {
        val (maze, situation) = parse(input)
        return situation.steps(maze).last().visited.map { it.position }.toSet().size
    }

    fun part2(input: String): Int {
        val (maze, startingGuardState) = parse(input)
        val originalRoute =
            startingGuardState
                .steps(maze)
                .last()
                .visited
                .map { it.position }
                .toSet()

        return originalRoute
            .map { routePoint ->
                runCatching {
                    startingGuardState.steps(maze.addBlock(routePoint)).last()
                    return@runCatching false
                }.recoverCatching {
                    return@recoverCatching when (it) {
                        is BeenHereException -> true
                        is IndexOutOfBoundsException -> false
                        is AlreadyBlockException -> false
                        is NoSuchElementException -> false
                        else -> throw it
                    }
                }.getOrThrow()
            }
            .filter { it }
            .count()
    }
}
