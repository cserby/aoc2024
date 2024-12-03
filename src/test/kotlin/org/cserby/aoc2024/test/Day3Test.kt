package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day3
import kotlin.test.*

class Day3Test {
    @Test
    fun part1example() {
        assertEquals(161, Day3.part1(Utils.readFile("/day3.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(42, Day3.part1(Utils.readFile("/day3.input.txt")))
    }
}
