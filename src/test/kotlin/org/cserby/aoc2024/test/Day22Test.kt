package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day22
import kotlin.test.Test
import kotlin.test.assertEquals

class Day22Test {
    @Test
    fun part1example() {
        assertEquals(37327623L, Day22.part1(Utils.readFile("/day22.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(13234715490L, Day22.part1(Utils.readFile("/day22.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(23, Day22.part2(Utils.readFile("/day22.example2.txt")))
    }

    @Test
    fun part2() {
        assertEquals(23, Day22.part2(Utils.readFile("/day22.input.txt")))
    }
}
