package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day23
import kotlin.test.Test
import kotlin.test.assertEquals

class Day23Test {
    @Test
    fun part1example() {
        assertEquals(7, Day23.part1(Utils.readFile("/day23.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(1240, Day23.part1(Utils.readFile("/day23.input.txt")))
    }
}
