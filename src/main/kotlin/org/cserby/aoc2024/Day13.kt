package org.cserby.aoc2024

typealias ClawPosition = Pair<Int, Int>

fun ClawPosition.add(other: ClawPosition): ClawPosition {
    return first + other.first to second + other.second
}

data class ClawMachine(
    val buttonA: ClawPosition,
    val buttonB: ClawPosition,
    val prize: ClawPosition,
) {
    val priceA = 3
    val priceB = 1

    fun minTokens(): Int? {
        // Cramers rule
        val delta = buttonA.first * buttonB.second - buttonA.second * buttonB.first
        val delta1 = prize.first * buttonB.second - prize.second * buttonB.first
        val delta2 = buttonA.first * prize.second - buttonA.second * prize.first
        if (delta1.mod(delta) != 0 || delta2.mod(delta) != 0) return null
        val pressA = delta1 / delta
        val pressB = delta2 / delta
        return pressB * priceB + pressA * priceA
    }
}

object Day13 {
    private fun parseClawMachine(input: List<String>): ClawMachine {
        val (buttonA, buttonB, prize) = input

        return ClawMachine(
            buttonA = buttonA.substringAfter("Button A: X+").split(", Y+").let { (x, y) -> x.toInt() to y.toInt() },
            buttonB = buttonB.substringAfter("Button B: X+").split(", Y+").let { (x, y) -> x.toInt() to y.toInt() },
            prize = prize.substringAfter("Prize: X=").split(", Y=").let { (x, y) -> x.toInt() to y.toInt() },
        )
    }

    private fun parse(input: String): List<ClawMachine> {
        return input.split("\n\n").map { parseClawMachine(it.lines()) }
    }

    fun part1(input: String): Int {
        return parse(input).fold(0) { acc, curr -> acc + (curr.minTokens() ?: 0) }
    }

    fun part2(input: String): Int {
        return 43
    }
}
