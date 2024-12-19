package org.cserby.aoc2024

object Day19 {
    private fun parse(input: String): Pair<List<String>, List<String>> {
        val lines = input.lines()
        val towels = lines[0].split(", ")

        val designs = lines.drop(2)

        return designs to towels
    }

    private fun possible(
        design: String,
        towels: List<String>,
    ): Boolean {
        if (design.isEmpty()) return true
        for (towel in towels) {
            if (design.startsWith(towel) && possible(design.substringAfter(towel), towels)) return true
        }
        return false
    }

    fun part1(input: String): Int {
        val (designs, towels) = parse(input)

        return designs.count { possible(it, towels) }
    }

    class MemoizedHowManyPossible(val towels: List<String>) {
        val memo: HashMap<String, Long> = HashMap()

        fun howManyPossible(design: String): Long {
            val memoed = memo.get(design)
            if (memoed != null) return memoed
            val calculated = howManyPossibleInternal(design)
            memo[design] = calculated
            return calculated
        }

        private fun howManyPossibleInternal(design: String): Long {
            if (design.isEmpty()) return 1L
            return towels.filter { design.startsWith(it) }.fold(0L) {
                    acc, towel ->
                acc + howManyPossible(design.substringAfter(towel))
            }
        }
    }

    fun part2(input: String): Long {
        val (designs, towels) = parse(input)

        return designs.fold(0L) { acc, design -> acc + MemoizedHowManyPossible(towels).howManyPossible(design) }
    }
}
