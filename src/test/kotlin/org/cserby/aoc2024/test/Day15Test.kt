package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day15
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day15Test {
    @Test
    fun part1example() {
        assertEquals(2028, Day15.part1(Utils.readFile("/day15.example.txt")))
    }

    @Test
    fun part1example2() {
        assertEquals(10092, Day15.part1(Utils.readFile("/day15.example2.txt")))
    }

    @Test
    fun part1() {
        assertEquals(1398947, Day15.part1(Utils.readFile("/day15.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(1751, Day15.part2(Utils.readFile("/day15.example.txt")))
    }

    @Test
    fun part2example2() {
        assertEquals(9021, Day15.part2(Utils.readFile("/day15.example2.txt")))
    }

    @Test
    fun part2() {
        assertEquals(1397393, Day15.part2(Utils.readFile("/day15.input.txt")))
    }
}
