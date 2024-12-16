package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day16
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day16Test {
    @Test
    fun part1example() {
        assertEquals(7036, Day16.part1(Utils.readFile("/day16.example.txt")))
    }

    @Test
    fun part1example2() {
        assertEquals(11048, Day16.part1(Utils.readFile("/day16.example2.txt")))
    }

    @Test
    fun part1() {
        assertEquals(98416, Day16.part1(Utils.readFile("/day16.input.txt")))
    }
}
