package org.cserby.aoc2024

import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

typealias FieldBounds = Pair<Int, Int>

data class Robot(
    val p: Pair<Int, Int>,
    val v: Pair<Int, Int>,
) {
    fun move(
        t: Int,
        f: FieldBounds,
    ): Pair<Int, Int> {
        return p.let { (px, py) ->
            v.let { (vx, vy) ->
                f.let { (fx, fy) ->
                    (px + t * vx).mod(fx) to (py + t * vy).mod(fy)
                }
            }
        }
    }
}

fun FieldBounds.quadrant(p: Pair<Int, Int>): Int? {
    return p.let { (px, py) ->
        val horizontalMiddle = (first - 1) / 2
        val verticalMiddle = (second - 1) / 2

        return if (px < horizontalMiddle) {
            return if (py < verticalMiddle) {
                1
            } else if (py > verticalMiddle) {
                2
            } else {
                null
            }
        } else if (px > horizontalMiddle) {
            return if (py < verticalMiddle) {
                3
            } else if (py > verticalMiddle) {
                4
            } else {
                null
            }
        } else {
            null
        }
    }
}

object Day14 {
    private fun parse(input: String): List<Robot> {
        return input.lines().map { line ->
            line.split(" ").let { (pPart, vPart) ->
                Robot(
                    p =
                        pPart.substringAfter("p=").split(",").let { (x, y) ->
                            x.toInt() to y.toInt()
                        },
                    v =
                        vPart.substringAfter("v=").split(",").let { (x, y) ->
                            x.toInt() to y.toInt()
                        },
                )
            }
        }
    }

    fun part1(
        input: String,
        fieldBounds: FieldBounds = 101 to 103,
    ): Int {
        return parse(input)
            .map { it.move(100, fieldBounds) }
            .groupingBy { fieldBounds.quadrant(it) }
            .eachCount()
            .entries
            .fold(1) { acc, (quadrant, robotCount) -> if (quadrant == null) acc else acc * robotCount }
    }

    private fun printField(
        robotPositions: Set<Pair<Int, Int>>,
        fieldBounds: FieldBounds,
    ): String {
        return (0..<fieldBounds.first).map { x ->
            (0..<fieldBounds.second).map { y ->
                if (robotPositions.contains(x to y)) "#" else "."
            }.joinToString("")
        }.joinToString("\n")
    }

    private fun zippedLength(clear: String): Int {
        val lengthOutputStream =
            object : OutputStream() {
                var length = 0

                override fun write(p0: Int) {
                    length++
                }
            }

        ZipOutputStream(lengthOutputStream).use { zos ->
            zos.putNextEntry(ZipEntry(""))
            zos.write(clear.toByteArray())
            zos.closeEntry()
        }

        return lengthOutputStream.length
    }

    fun part2(input: String): Int {
        var robots = parse(input)
        val fieldBounds = 101 to 103
        return generateSequence(1) { it + 1 }.map { i ->
            val fld = printField(robots.map { it.move(i, fieldBounds) }.toSet(), fieldBounds)
            i to (fld to zippedLength(fld))
        }.first { it.second.second < 800 }.let {
            println(it.second.first)
            it.first
        }
    }
}
