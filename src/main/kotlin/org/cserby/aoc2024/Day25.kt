package org.cserby.aoc2024

object Day25 {
    fun parsePart(input: String): List<Int> {
        val lines = input.lines()

        return lines[0].indices.map { y ->
            lines.takeWhile { it[y] == lines[0][y] }.count()
        }
    }

    fun parse(input: String): Pair<List<List<Int>>, List<List<Int>>> {
        return input.split("\n\n")
            .fold(emptyList<List<Int>>() to emptyList<List<Int>>()) { (keys, locks), part ->
                when (part.first()) {
                    '.' -> // key
                        keys.plus(listOf(parsePart(part))) to locks
                    '#' -> // lock
                        keys to locks.plus(listOf(parsePart(part)))
                    else -> throw Exception("Unexpected first char ${part.first()}")
                }
            }
    }

    fun part1(input: String): Int {
        val (keys, locks) = parse(input)
        val keysWithLocksMatch =
            keys.flatMap { key ->
                locks.map { lock ->
                    key.zip(lock).all { it.first >= it.second }
                }
            }
        return keysWithLocksMatch.count { it }
    }
}
