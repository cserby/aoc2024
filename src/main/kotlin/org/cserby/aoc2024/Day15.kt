package org.cserby.aoc2024

import org.cserby.aoc2024.Utils.Direction

object Day15 {
    enum class WarehouseField(val chr: Char) {
        EMPTY('.'),
        BOX('O'),
        WALL('#'),
        BOX_L('['),
        BOX_R(']'),
    }

    data class Warehouse(
        val fields: List<List<WarehouseField>>,
        val robotPosition: Pair<Int, Int>,
    ) {
        private fun cell(point: Pair<Int, Int>): WarehouseField {
            return fields[point.first][point.second]
        }

        private fun setCell(
            point: Pair<Int, Int>,
            field: WarehouseField = WarehouseField.BOX,
        ): Warehouse {
            return copy(
                fields =
                    fields.mapIndexed { x, line ->
                        if (x != point.first) {
                            line
                        } else {
                            line.mapIndexed { y, fld ->
                                if (y != point.second) fld else field
                            }
                        }
                    },
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

        class HitWallException(point: Pair<Int, Int>) : Exception("Hit wall at <$point>")

        private fun move(
            point: Pair<Int, Int>,
            direction: Direction,
        ): Warehouse {
            val cellValue = cell(point)
            val moveTo = neighborPoint(point, direction)

            return setCell(point, WarehouseField.EMPTY).let { wh ->
                when (cell(moveTo)) {
                    WarehouseField.EMPTY -> wh
                    WarehouseField.BOX -> wh.move(moveTo, direction)
                    WarehouseField.WALL -> throw HitWallException(moveTo)
                    WarehouseField.BOX_L ->
                        when (direction) {
                            Direction.RIGHT -> wh.move(moveTo, direction)
                            Direction.UP -> wh.move(moveTo, direction).move(neighborPoint(moveTo, Direction.RIGHT), direction)
                            Direction.DOWN -> wh.move(moveTo, direction).move(neighborPoint(moveTo, Direction.RIGHT), direction)
                            Direction.LEFT -> wh.move(moveTo, direction)
                        }
                    WarehouseField.BOX_R ->
                        when (direction) {
                            Direction.RIGHT -> wh.move(moveTo, direction)
                            Direction.UP -> wh.move(moveTo, direction).move(neighborPoint(moveTo, Direction.LEFT), direction)
                            Direction.DOWN -> wh.move(moveTo, direction).move(neighborPoint(moveTo, Direction.LEFT), direction)
                            Direction.LEFT -> wh.move(moveTo, direction)
                        }
                }
            }.setCell(moveTo, cellValue)
        }

        fun moveRobot(direction: Direction): Warehouse {
            val moveTo = neighborPoint(robotPosition, direction)

            return runCatching {
                return when (cell(moveTo)) {
                    WarehouseField.WALL -> throw HitWallException(moveTo)
                    WarehouseField.EMPTY -> this
                    WarehouseField.BOX -> move(moveTo, direction)
                    WarehouseField.BOX_R ->
                        when (direction) {
                            Direction.DOWN -> move(moveTo, direction).move(neighborPoint(moveTo, Direction.LEFT), direction)
                            Direction.UP -> move(moveTo, direction).move(neighborPoint(moveTo, Direction.LEFT), direction)
                            Direction.LEFT -> move(moveTo, direction)
                            Direction.RIGHT -> throw Exception("Can't hit box's right side while moving right at $moveTo")
                        }
                    WarehouseField.BOX_L ->
                        when (direction) {
                            Direction.DOWN ->
                                move(moveTo, direction).move(
                                    neighborPoint(moveTo, Direction.RIGHT),
                                    direction,
                                )

                            Direction.UP -> move(moveTo, direction).move(neighborPoint(moveTo, Direction.RIGHT), direction)
                            Direction.RIGHT -> move(moveTo, direction)
                            Direction.LEFT -> throw Exception("Can't hit box's left side while moving left at $moveTo")
                        }
                }.copy(robotPosition = moveTo)
            }.getOrDefault(this)
        }

        fun boxCoords(): List<Pair<Int, Int>> {
            return fields.flatMapIndexed { x, line ->
                line.mapIndexed { y, fld ->
                    if (fld == WarehouseField.BOX || fld == WarehouseField.BOX_L) x to y else null
                }
            }.filterNotNull()
        }

        fun display(): String {
            return fields.indices.map { x ->
                fields[x].indices.map { y ->
                    if (robotPosition == x to y) {
                        '@'
                    } else {
                        fields[x][y].chr
                    }
                }.joinToString("")
            }.joinToString("\n")
        }

        companion object {
            fun parse(whInput: String): Warehouse {
                var robotPos: Position? = null
                val warehouseFields =
                    whInput.lines().mapIndexed { x, line ->
                        line.toCharArray().mapIndexed { y, field ->
                            when (field) {
                                '#' -> WarehouseField.WALL
                                'O' -> WarehouseField.BOX
                                '@' -> {
                                    robotPos = x to y
                                    WarehouseField.EMPTY
                                }

                                '.' -> WarehouseField.EMPTY
                                else -> throw Exception("Unknown warehouse field at ($x, $y): $field")
                            }
                        }
                    }
                return Warehouse(fields = warehouseFields, robotPosition = robotPos!!)
            }

            fun parse2(whInput: String): Warehouse {
                var robotPos: Position? = null
                val warehouseFields =
                    whInput.lines().mapIndexed { x, line ->
                        line.toCharArray().flatMapIndexed { y, field ->
                            when (field) {
                                '#' -> listOf(WarehouseField.WALL, WarehouseField.WALL)
                                'O' -> listOf(WarehouseField.BOX_L, WarehouseField.BOX_R)
                                '@' -> {
                                    robotPos = x to y
                                    listOf(WarehouseField.EMPTY, WarehouseField.EMPTY)
                                }

                                '.' -> listOf(WarehouseField.EMPTY, WarehouseField.EMPTY)
                                else -> throw Exception("Unknown warehouse field at ($x, $y): $field")
                            }
                        }
                    }
                return Warehouse(fields = warehouseFields, robotPosition = robotPos!!)
            }
        }
    }

    private fun parse(
        input: String,
        parseWh: (String) -> Warehouse = Warehouse::parse,
    ): Pair<Warehouse, List<Direction>> {
        return input.split("\n\n").let { (whStr, stepsStr) ->
            return parseWh(whStr) to
                stepsStr.lines().joinToString("").toCharArray()
                    .map { Direction.fromChar(it) }
        }
    }

    fun part1(input: String): Int {
        val (warehouse, robotSteps) = parse(input)
        val eventualWh =
            robotSteps.fold(warehouse) { wh, dir ->
                val newWh = wh.moveRobot(dir)
                newWh
            }
        return eventualWh.boxCoords().fold(0) { acc, (boxX, boxY) ->
            acc + 100 * boxX + boxY
        }
    }

    fun part2(input: String): Int {
        val (wrhs, robotSteps) = parse(input, Warehouse::parse2)
        val warehouse = wrhs.copy(robotPosition = wrhs.robotPosition.let { (x, y) -> x to 2 * y })
        val eventualWh =
            robotSteps.fold(warehouse) { wh, dir ->
                val newWh = wh.moveRobot(dir)
                println("Move: $dir")
                println(newWh.display())
                println()
                newWh
            }
        return eventualWh.boxCoords().fold(0) { acc, (boxX, boxY) ->
            acc + 100 * boxX + boxY
        }
    }
}
