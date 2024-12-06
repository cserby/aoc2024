package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day6
import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Test {
    @Test
    fun part1example() {
        assertEquals(41, Day6.part1(Utils.readFile("/day6.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(4982, Day6.part1(Utils.readFile("/day6.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(6, Day6.part2(Utils.readFile("/day6.example.txt")))
    }

    @Test
    fun part2() {
        // Too high, but fine for some other input
        assertEquals(1697, Day6.part2(Utils.readFile("/day6.input.txt")))
    }
}
