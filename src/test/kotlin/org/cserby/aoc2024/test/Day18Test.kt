package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day18
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day18Test {
    @Test
    fun part1example() {
        assertEquals(22, Day18.part1(Utils.readFile("/day18.example.txt"), memorySpaceEnd = 6, corruptedBytes = 12))
    }

    @Test
    fun part1() {
        assertEquals(340, Day18.part1(Utils.readFile("/day18.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals("6,1", Day18.part2(Utils.readFile("/day18.example.txt"), memorySpaceEnd = 6))
    }

    @Test
    fun part2() {
        assertEquals("34,32", Day18.part2(Utils.readFile("/day18.input.txt")))
    }
}
