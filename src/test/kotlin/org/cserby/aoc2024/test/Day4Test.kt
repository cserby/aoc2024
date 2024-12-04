package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day4
import kotlin.test.*

class Day4Test {
    @Test
    fun part1example() {
        assertEquals(18, Day4.part1(Utils.readFile("/day4.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(2547, Day4.part1(Utils.readFile("/day4.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(9, Day4.part2(Utils.readFile("/day4.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(1939, Day4.part2(Utils.readFile("/day4.input.txt")))
    }
}
