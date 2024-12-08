package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day8
import kotlin.test.Test
import kotlin.test.assertEquals

class Day8Test {
    @Test
    fun interference() {
        assertEquals(setOf(1 to 3, 7 to 6), Day8.interference(3 to 4, 5 to 5))
    }

    @Test
    fun part1example() {
        assertEquals(14, Day8.part1(Utils.readFile("/day8.example.txt")))
    }

    @Test
    fun part1example2() {
        assertEquals(4, Day8.part1(Utils.readFile("/day8.example2.txt")))
    }

    @Test
    fun part1() {
        assertEquals(341, Day8.part1(Utils.readFile("/day8.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(53, Day8.part2(Utils.readFile("/day8.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(53, Day8.part2(Utils.readFile("/day8.input.txt")))
    }
}
