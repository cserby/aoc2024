package org.cserby.aoc2024.test

import org.cserby.aoc2024.day12.Day12
import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {
    @Test
    fun part1example() {
        assertEquals(1930, Day12.part1(Utils.readFile("/day12.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(216042, Day12.part1(Utils.readFile("/day12.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(1206, Day12.part2(Utils.readFile("/day12.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(923480, Day12.part2(Utils.readFile("/day12.input.txt")))
    }
}
