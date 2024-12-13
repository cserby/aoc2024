package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day13
import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {
    @Test
    fun part1example() {
        assertEquals(480, Day13.part1(Utils.readFile("/day13.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(34393, Day13.part1(Utils.readFile("/day13.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(1206, Day13.part2(Utils.readFile("/day13.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(923480, Day13.part2(Utils.readFile("/day13.input.txt")))
    }
}
