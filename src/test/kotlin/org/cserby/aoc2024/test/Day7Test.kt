package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day7
import kotlin.test.Test
import kotlin.test.assertEquals

class Day7Test {
    @Test
    fun part1example() {
        assertEquals(3749, Day7.part1(Utils.readFile("/day7.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(1545311493300, Day7.part1(Utils.readFile("/day7.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(6, Day7.part2(Utils.readFile("/day7.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(1697, Day7.part2(Utils.readFile("/day7.input.txt")))
    }
}
