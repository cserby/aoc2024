package org.cserby.aoc2024

import org.cserby.aoc2024.Utils.Direction

object Day15 {
    enum class WarehouseField(val chr: Char) {
        EMPTY('.'),
        BOX('O'),
        WALL('#'),
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

        private fun moveBox(
            point: Pair<Int, Int>,
            direction: Direction,
        ): Warehouse {
            val moveTo = neighborPoint(point, direction)

            return when (cell(moveTo)) {
                WarehouseField.EMPTY -> setCell(point, WarehouseField.EMPTY).setCell(moveTo, WarehouseField.BOX)
                WarehouseField.BOX -> setCell(point, WarehouseField.EMPTY).moveBox(moveTo, direction).setCell(moveTo, WarehouseField.BOX)
                WarehouseField.WALL -> throw HitWallException(moveTo)
            }
        }

        fun moveRobot(direction: Direction): Warehouse {
            val moveTo = neighborPoint(robotPosition, direction)

            return runCatching {
                return when (cell(moveTo)) {
                    WarehouseField.WALL -> throw HitWallException(moveTo)
                    WarehouseField.EMPTY -> copy(robotPosition = moveTo)
                    WarehouseField.BOX -> moveBox(moveTo, direction).copy(robotPosition = moveTo)
                }
            }.getOrDefault(this)
        }

        fun boxCoords(): List<Pair<Int, Int>> {
            return fields.flatMapIndexed { x, line ->
                line.mapIndexed { y, fld ->
                    if (fld == WarehouseField.BOX) x to y else null
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
        }
    }

    private fun parse(input: String): Pair<Warehouse, List<Direction>> {
        return input.split("\n\n").let { (whStr, stepsStr) ->
            return Warehouse.parse(whStr) to
                stepsStr.lines().joinToString("").toCharArray()
                    .map { Direction.fromChar(it) }
        }
    }

    fun part1(input: String): Int {
        val (warehouse, robotSteps) = parse(input)
        val eventualWh =
            robotSteps.fold(warehouse) { wh, dir ->
                val newWh = wh.moveRobot(dir)
                println(newWh.display())
                newWh
            }
        return eventualWh.boxCoords().fold(0) { acc, (boxX, boxY) ->
            acc + 100 * boxX + boxY
        }
    }

    fun part2(input: String): Int {
        return 5
    }
}
