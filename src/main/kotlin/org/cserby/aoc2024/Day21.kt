package org.cserby.aoc2024

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

    fun keypadSteps(keys: List<Char>): Sequence<Char> =
        sequence {
            var state = 'A'
            for (key in keys) {
                val (currX, currY) = KeypadPositions[state]!!
                val (toX, toY) = KeypadPositions[key]!!

                if (toX <= currX) {
                    (toX..<currX).forEach { yield('^') }
                    if (toY <= currY) {
                        (toY..<currY).forEach { yield('<') }
                    } else {
                        (currY..<toY).forEach { yield('>') }
                    }
                } else {
                    if (toY <= currY) {
                        (toY..<currY).forEach { yield('<') }
                    } else {
                        (currY..<toY).forEach { yield('>') }
                    }
                    (currX..<toX).forEach { yield('v') }
                }

                yield('A')
                state = key
            }
        }

    fun directionalSteps(steps: Sequence<Char>): Sequence<Char> =
        sequence {
            var state = 'A'

            steps.forEach { step ->
                val (currX, currY) = DirectionalPositions[state]!!
                val (toX, toY) = DirectionalPositions[step]!!

                if (toX <= currX) {
                    if (toY <= currY) {
                        (toY..<currY).forEach { yield('<') }
                    } else {
                        (currY..<toY).forEach { yield('>') }
                    }
                    (toX..<currX).forEach { yield('^') }
                } else {
                    (currX..<toX).forEach { yield('v') }
                    if (toY <= currY) {
                        (toY..<currY).forEach { yield('<') }
                    } else {
                        (currY..<toY).forEach { yield('>') }
                    }
                }

                yield('A')
                state = step
            }
        }

    fun part1(input: String): Int {
        return input.lines().fold(0) { acc, line ->
            val seqLen = directionalSteps(directionalSteps(keypadSteps(line.toCharArray().toList()))).toList().size
            val numVal = line.substring(0, line.length - 1).toInt()
            acc + numVal * seqLen
        }
    }
}
