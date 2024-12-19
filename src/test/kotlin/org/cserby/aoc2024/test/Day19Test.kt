package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day19
import kotlin.test.Test
import kotlin.test.assertEquals

class Day19Test {
    @Test
    fun part1example() {
        assertEquals(6, Day19.part1(Utils.readFile("/day19.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(267, Day19.part1(Utils.readFile("/day19.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(16L, Day19.part2(Utils.readFile("/day19.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(796449099271652L, Day19.part2(Utils.readFile("/day19.input.txt")))
    }
}
