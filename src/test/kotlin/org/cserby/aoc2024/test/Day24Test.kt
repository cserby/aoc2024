package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day24
import kotlin.test.Test
import kotlin.test.assertEquals

class Day24Test {
    @Test
    fun part1example() {
        assertEquals(2024L, Day24.part1(Utils.readFile("/day24.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(53755311654662L, Day24.part1(Utils.readFile("/day24.input.txt")))
    }

    @Test
    fun part2() {
        assertEquals("dkr,ggk,hhh,htp,rhv,z05,z15,z20", Day24.part2(Utils.readFile("/day24.input.txt")))
    }
}
