package org.cserby.aoc2024.test

import org.cserby.aoc2024.Day17
import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Test {
    @Test
    fun part1example() {
        assertEquals("4,6,3,5,6,3,5,2,1,0", Day17.part1(Utils.readFile("/day17.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals("1,5,0,5,2,0,1,3,5", Day17.part1(Utils.readFile("/day17.input.txt")))
    }

    @Test
    fun part2part1() {
        assertEquals("1,5,0,5,2,0,1,3,5", Day17.computer2(45483412).joinToString(","))
    }

    @Test
    fun part2example() {
        assertEquals(117440, Day17.part2(Utils.readFile("/day17.example2.txt")))
    }

    @Test
    fun part2() {
        assertEquals(236581108670061L, Day17.part2(Utils.readFile("/day17.input.txt")))
    }
}
