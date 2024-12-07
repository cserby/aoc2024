package org.cserby.aoc2024

fun <E> List<E>.splitHead(): Pair<E, List<E>> {
    val head = get(0)
    val tail = drop(1)
    return head to tail
}

data class Equation(val result: Long, val numbers: List<Long>) {
    private fun canBeTrueRec(
        numbersLeft: List<Long>,
        resultSoFar: Long,
        allowConcat: Boolean = false,
    ): Boolean {
        if (numbersLeft.isEmpty()) return resultSoFar == result
        if (resultSoFar > result) return false
        val (head, tail) = numbersLeft.splitHead()
        return canBeTrueRec(tail, resultSoFar + head, allowConcat) ||
            canBeTrueRec(tail, resultSoFar * head, allowConcat) ||
            (allowConcat && canBeTrueRec(tail, concatenate(resultSoFar, head), allowConcat))
    }

    fun canBeTrue(allowConcat: Boolean = false): Boolean {
        val (head, tail) = numbers.splitHead()
        return canBeTrueRec(tail, head, allowConcat)
    }

    private fun concatenate(
        first: Long,
        second: Long,
    ): Long {
        return "$first$second".toLong()
    }
}

object Day7 {
    fun parse(input: String): List<Equation> {
        return input.lines().map {
            val parts = it.split(": ")
            val numbers = parts[1].split(' ').map { it.toLong() }
            return@map Equation(result = parts[0].toLong(), numbers = numbers)
        }
    }

    fun part1(input: String): Long {
        return parse(input).filter { it.canBeTrue() }.map { it.result }.sum()
    }

    fun part2(input: String): Long {
        return parse(input).filter { it.canBeTrue(allowConcat = true) }.map { it.result }.sum()
    }
}
