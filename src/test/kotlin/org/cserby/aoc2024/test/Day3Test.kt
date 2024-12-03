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
        assertEquals(169021493, Day3.part1(Utils.readFile("/day3.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(48, Day3.part2(Utils.readFile("/day3.example.txt")))
    }
}
