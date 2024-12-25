package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day25
import kotlin.test.Test
import kotlin.test.assertEquals

class Day25Test {
    @Test
    fun part1example() {
        assertEquals(3, Day25.part1(Utils.readFile("/day25.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(2993, Day25.part1(Utils.readFile("/day25.input.txt")))
    }
}
