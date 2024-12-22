package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day21
import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {
    @Test
    fun part1example() {
        assertEquals(126384, Day21.part1(Utils.readFile("/day21.example.txt")))
    }

    @Test
    fun part1() {
        // 211246 too high
        assertEquals(126384, Day21.part1(Utils.readFile("/day21.input.txt")))
    }
}
