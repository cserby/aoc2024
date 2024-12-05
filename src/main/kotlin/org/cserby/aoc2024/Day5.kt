package org.cserby.aoc2024

typealias Ordering = Map<Int, List<Int>>
typealias Update = List<Int>

class OrderingComparator(private val ordering: Ordering) : Comparator<Int> {
    /*
    Compares its two arguments for order.
    Returns zero if the arguments are equal,
    a negative number if the first argument is less than the second,
    or a positive number if the first argument is greater than the second.

    From: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-comparator/
     */
    override fun compare(first: Int?, second: Int?): Int {
        if (first == null) throw Exception("First is null")
        if (second == null) throw Exception("Second is null")

        if (ordering.getOrDefault(first, emptyList()).contains(second)) return -1
        if (ordering.getOrDefault(second, emptyList()).contains(first)) return 1
        return 0
    }
}

private fun Update.orderWith(ordering: Ordering): Update {
    return sortedWith(OrderingComparator(ordering))
}

fun Update.isOrdered(ordering: Ordering): Boolean {
    return this.orderWith(ordering) == this
}

fun Update.middlePage(): Int {
    return get(size / 2)
}

object Day5 {
    private fun parse(input: String): Pair<Ordering, List<Update>> {
        val lines = input.lines()
        val orderingLines = lines.takeWhile { it.isNotEmpty() }
        val updatePagesLines = lines.dropWhile { it.isNotEmpty() }.drop(1)
        return parseOrdering(orderingLines) to parseUpdatePagesLines(updatePagesLines)
    }

    private fun parseOrdering(lines: List<String>): Ordering {
        return lines
            .map { it.split('|').map { it.toInt() } }
            .groupBy { it[0] }
            .mapValues { (key, value) -> value.flatten().filter { it != key } }
    }

    private fun parseUpdatePagesLines(lines: List<String>): List<Update> {
        return lines
            .map { it.split(',').map { it.toInt() }}
    }

    fun part1(input: String): Int {
        val (ordering, updates) = parse(input)
        return updates.filter { it.isOrdered(ordering) }.map { it.middlePage() }.sum()
    }
}