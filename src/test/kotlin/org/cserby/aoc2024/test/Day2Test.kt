package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day2
import kotlin.test.*

class Day2Test {
    @Test
    fun part1example() {
        assertEquals(2, Day2.part1(Utils.readFile("/day2.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(490, Day2.part1(Utils.readFile("/day2.input.txt")))
    }
}
