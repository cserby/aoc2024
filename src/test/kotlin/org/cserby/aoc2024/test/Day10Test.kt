package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day10
import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {
    @Test
    fun part1example() {
        assertEquals(1, Day10.part1(Utils.readFile("/day10.example.txt")))
    }

    @Test
    fun part1example2() {
        assertEquals(36, Day10.part1(Utils.readFile("/day10.example2.txt")))
    }

    @Test
    fun part1() {
        assertEquals(798, Day10.part1(Utils.readFile("/day10.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(2858, Day10.part2(Utils.readFile("/day10.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(4, Day10.part2(Utils.readFile("/day10.input.txt")))
    }
}
