package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day21
import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {
    @Test
    fun part1example() {
        assertEquals(126384L, Day21.part1(Utils.readFile("/day21.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(203814L, Day21.part1(Utils.readFile("/day21.input.txt")))
    }

    @Test
    fun part2() {
        assertEquals(248566068436630L, Day21.part2(Utils.readFile("/day21.input.txt")))
    }
}
