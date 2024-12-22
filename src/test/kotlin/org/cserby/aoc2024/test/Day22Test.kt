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
}
