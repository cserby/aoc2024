package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day14
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14Test {
    @Test
    fun part1example() {
        assertEquals(12, Day14.part1(Utils.readFile("/day14.example.txt"), 11 to 7))
    }

    @Test
    fun part1() {
        assertEquals(221827598, Day14.part1(Utils.readFile("/day14.input.txt")))
    }
}
