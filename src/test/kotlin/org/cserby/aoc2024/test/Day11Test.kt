package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day11
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {
    @Test
    fun part1example() {
        assertEquals(22L, Day11.part1(Utils.readFile("/day11.example.txt"), blinks = 6))
    }

    @Test
    fun part1example2() {
        assertEquals(55312L, Day11.part1(Utils.readFile("/day11.example.txt"), blinks = 25))
    }

    @Test
    fun part1() {
        assertEquals(216042L, Day11.part1(Utils.readFile("/day11.input.txt"), blinks = 25))
    }

    @Test
    fun part2() {
        assertEquals(255758646442399L, Day11.part2(Utils.readFile("/day11.input.txt")))
    }
}
