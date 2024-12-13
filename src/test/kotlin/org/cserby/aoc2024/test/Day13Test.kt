package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day13
import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {
    @Test
    fun part1example() {
        assertEquals(480L, Day13.part1(Utils.readFile("/day13.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(34393L, Day13.part1(Utils.readFile("/day13.input.txt")))
    }

    @Test
    fun part2() {
        assertEquals(83551068361379L, Day13.part2(Utils.readFile("/day13.input.txt")))
    }
}
