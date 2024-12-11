package org.cserby.aoc2024

import kotlin.collections.fold

object MemoizedBlink {
    val memo: HashMap<Pair<Long, Int>, Long> = HashMap()

    private fun howManyStonesInternal(
        stone: Long,
        blinks: Int,
    ): Long {
        if (blinks == 0) return 1
        if (stone == 0L) return howManyStones(1L, blinks - 1)
        val stoneStr = stone.toString()
        if (stoneStr.length % 2 == 0) {
            return howManyStones(stoneStr.substring(0, stoneStr.length / 2).toLong(), blinks - 1) +
                howManyStones(stoneStr.substring(stoneStr.length / 2).toLong(), blinks - 1)
        }
        return howManyStones(stone * 2024, blinks - 1)
    }

    fun howManyStones(
        stone: Long,
        blinks: Int,
    ): Long {
        val memoed = memo.get(stone to blinks)
        if (memoed != null) return memoed
        val calculated = howManyStonesInternal(stone, blinks)
        memo[stone to blinks] = calculated
        return calculated
    }
}

object Day11 {
    private fun parse(input: String): List<Long> {
        return input.split(" ")
            .map { it.toLong() }
    }

    fun part1(
        input: String,
        blinks: Int = 25,
    ): Long {
        return parse(input).fold(0L) { acc, stone ->
            acc + MemoizedBlink.howManyStones(stone, blinks)
        }
    }

    fun part2(input: String): Long {
        return part1(input, blinks = 75)
    }
}
