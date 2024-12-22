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

    class MemoizedLineCosts {
        val memo: HashMap<Pair<String, List<(Char, Char) -> Set<String>>>, Long> = HashMap()

        fun lineCost(
            line: String,
            keypadStepGenerators: List<(Char, Char) -> Set<String>> =
                listOf(Day21::keypadSteps, Day21::directionalSteps, Day21::directionalSteps),
        ): Long {
            val memoed = memo.get(line to keypadStepGenerators)
            if (memoed != null) return memoed
            val calculated = lineCostInternal(line, keypadStepGenerators)
            memo[line to keypadStepGenerators] = calculated
            return calculated
        }

        private fun lineCostInternal(
            line: String,
            keypadStepGenerators: List<(Char, Char) -> Set<String>> =
                listOf(Day21::keypadSteps, Day21::directionalSteps, Day21::directionalSteps),
        ): Long {
            if (keypadStepGenerators.isEmpty()) return line.length.toLong()
            return ("A$line").zipWithNext()
                .map { keypadStepGenerators[0](it.first, it.second) }
                .map { stateTransitionStepAlternatives ->
                    stateTransitionStepAlternatives.map { lineCost(it, keypadStepGenerators.drop(1)) }.min()
                }.sum()
        }
    }

    fun part1(input: String): Long {
        val mlc = MemoizedLineCosts()
        return input.lines()
            .map { line -> line to mlc.lineCost(line, listOf(Day21::keypadSteps, Day21::directionalSteps, Day21::directionalSteps)) }
            .map { (line, cost) -> line.substring(0, line.length - 1).toInt() * cost }
            .sum()
    }

    fun part2(input: String): Long {
        val mlc = MemoizedLineCosts()
        return input.lines()
            .map { line ->
                line to
                    mlc.lineCost(
                        line,
                        listOf(Day21::keypadSteps) + (1..25).map { Day21::directionalSteps },
                    )
            }
            .map { (line, cost) -> line.substring(0, line.length - 1).toInt() * cost }
            .sum()
    }
}
