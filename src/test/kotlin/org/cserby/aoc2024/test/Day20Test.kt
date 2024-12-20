package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day20
import kotlin.test.Test
import kotlin.test.assertEquals

class Day20Test {
    @Test
    fun part1example() {
        assertEquals(0, Day20.part1(Utils.readFile("/day20.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(1429, Day20.part1(Utils.readFile("/day20.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(0, Day20.part2(Utils.readFile("/day20.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(988931, Day20.part2(Utils.readFile("/day20.input.txt")))
    }
}
