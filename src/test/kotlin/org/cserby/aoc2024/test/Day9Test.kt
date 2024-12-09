package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day9
import kotlin.test.Test
import kotlin.test.assertEquals

class Day9Test {
    @Test
    fun part1example() {
        assertEquals(1928, Day9.part1(Utils.readFile("/day9.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(6359213660505L, Day9.part1(Utils.readFile("/day9.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(43, Day9.part2(Utils.readFile("/day9.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(43, Day9.part2(Utils.readFile("/day9.input.txt")))
    }
}
