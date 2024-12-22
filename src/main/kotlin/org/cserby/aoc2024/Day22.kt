package org.cserby.aoc2024

object Day22 {
    data class Buyer(val initialSecret: Long) {
        private fun advance(actual: Long): Long {
            val first = actual.xor(actual.shl(6)).mod(16777216L) // 2^24
            val second = first.xor(first.shr(5)).mod(16777216L)
            val third = second.xor(second.shl(11)).mod(16777216L)

            return third
        }

        fun secretNumbers(): Sequence<Long> =
            sequence {
                var actual = initialSecret
                while (true) {
                    yield(actual)
                    actual = advance(actual)
                }
            }
    }

    fun part1(input: String): Long {
        return input.lines()
            .map { Buyer(it.toLong()) }
            .map { it.secretNumbers() }
            .map { it.elementAt(2000) }
            .fold(0) { acc, secret -> acc + secret }
    }
}
