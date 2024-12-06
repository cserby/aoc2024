package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day5
import kotlin.test.Test
import kotlin.test.assertEquals

class Day5Test {
    @Test
    fun part1example() {
        assertEquals(143, Day5.part1(Utils.readFile("/day5.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(2547, Day5.part1(Utils.readFile("/day5.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(123, Day5.part2(Utils.readFile("/day5.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(5285, Day5.part2(Utils.readFile("/day5.input.txt")))
    }
}
