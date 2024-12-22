package org.cserby.aoc2024

import kotlin.collections.map

object Day21 {
    /*
+---+---+---+
| 7 | 8 | 9 |
+---+---+---+
| 4 | 5 | 6 |
+---+---+---+
| 1 | 2 | 3 |
+---+---+---+
    | 0 | A |
    +---+---+

    +---+---+
    | ^ | A |
+---+---+---+
| < | v | > |
+---+---+---+
     */
    val KeypadPositions =
        mapOf<Char, Pair<Int, Int>>(
            '7' to (0 to 0),
            '8' to (0 to 1),
            '9' to (0 to 2),
            '4' to (1 to 0),
            '5' to (1 to 1),
            '6' to (1 to 2),
            '1' to (2 to 0),
            '2' to (2 to 1),
            '3' to (2 to 2),
            '0' to (3 to 1),
            'A' to (3 to 2),
        )

    val DirectionalPositions =
        mapOf<Char, Pair<Int, Int>>(
            '^' to (0 to 1),
            'A' to (0 to 2),
            '<' to (1 to 0),
            'v' to (1 to 1),
            '>' to (1 to 2),
        )

    fun keypadSteps(
        state: Char,
        to: Char,
    ): Set<String> {
        val (stateX, stateY) = KeypadPositions[state]!!
        val (toX, toY) = KeypadPositions[to]!!

        val xAdjust = (if (toX <= stateX) (toX..<stateX).map { '^' } else (stateX..<toX).map { 'v' }).joinToString("")
        val yAdjust = (if (toY <= stateY) (toY..<stateY).map { '<' } else (stateY..<toY).map { '>' }).joinToString("")

        if (stateX == 3 && toY == 0) return emptySet<String>().plus("$xAdjust${yAdjust}A")
        if (stateY == 0 && toX == 3) return emptySet<String>().plus("$yAdjust${xAdjust}A")
        return setOf("$yAdjust${xAdjust}A", "$xAdjust${yAdjust}A")
    }

    fun directionalSteps(
        state: Char,
        to: Char,
    ): Set<String> {
        val (stateX, stateY) = DirectionalPositions[state]!!
        val (toX, toY) = DirectionalPositions[to]!!

        val xAdjust = (if (toX <= stateX) (toX..<stateX).map { '^' } else (stateX..<toX).map { 'v' }).joinToString("")
        val yAdjust = (if (toY <= stateY) (toY..<stateY).map { '<' } else (stateY..<toY).map { '>' }).joinToString("")

        if (stateX == 0 && toY == 0) return emptySet<String>().plus("$xAdjust${yAdjust}A")
        if (stateY == 0 && toX == 0) return emptySet<String>().plus("$yAdjust${xAdjust}A")
        return setOf("$yAdjust${xAdjust}A", "$xAdjust${yAdjust}A")
    }

    fun firstRobotSteps(keycode: String): Sequence<Char> =
        sequence {
            var keypadState = 'A'
            var directionalState = 'A'

            for (key in keycode.toList()) {
                val possibleSteps = keypadSteps(keypadState, key)
                val preferredSteps =
                    runCatching {
                        possibleSteps.first {
                            it.startsWith(
                                directionalState,
                            )
                        }
                    }.getOrElse { possibleSteps.take(1)[0] }
                yieldAll(preferredSteps.toList())
                keypadState = key
                directionalState = preferredSteps.toList().last()
            }
        }

    fun subsequentRobotSteps(nextRobotSteps: Sequence<Char>): Sequence<Char> =
        sequence {
            var directionalState = 'A'

            nextRobotSteps.forEach { nextRobotStep ->
                val possibleSteps = directionalSteps(directionalState, nextRobotStep)
                val preferredSteps =
                    runCatching {
                        possibleSteps.first {
                            it.startsWith(
                                directionalState,
                            )
                        }
                    }.getOrElse { possibleSteps.take(1)[0] }
                yieldAll(preferredSteps.toList())
                directionalState = nextRobotStep
            }
        }

    fun part1(input: String): Int {
        return input.lines()
            .map { line -> line to firstRobotSteps(line) }
            .map { (line, frs) -> line to subsequentRobotSteps(frs) }
            .map { (line, srs) -> line to subsequentRobotSteps(srs) }
            .fold(0) { acc, (line, srs) ->
                val seqLen = srs.toList().size
                val numVal = line.substring(0, line.length - 1).toInt()
                acc + numVal * seqLen
            }
    }
}
