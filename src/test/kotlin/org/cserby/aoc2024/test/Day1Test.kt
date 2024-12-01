package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day1
import kotlin.test.*

class Day1Test {
    @Test
    fun part1example() {
        assertEquals(11, Day1.part1(Utils.readFile("/day1.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(1590491, Day1.part1(Utils.readFile("/day1.input.txt")))
    }
}