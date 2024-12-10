package org.cserby.aoc2024

typealias TopoMap = List<List<Int>>
typealias ReachableMap = MutableList<MutableList<Set<Coord>>>
typealias Coord = Pair<Int, Int>

fun TopoMap.allCells(): Sequence<Pair<Int, Int>> =
    sequence {
        for (x in 0..<size) {
            for (y in 0..<get(x).size) {
                yield(x to y)
            }
        }
    }


fun TopoMap.pointsOfHeight(height: Int): Sequence<Pair<Int, Int>> = sequence {
    yieldAll(allCells().filter { (x, y) -> get(x)[y] == height })
}

fun TopoMap.neighborsOneHigher(coord: Coord): Sequence<Pair<Int, Int>> = sequence {
    val (x, y) = coord
    for (dx in arrayOf(-1, 1)) {
        try {
            val x1 = x + dx
            if (get(x1)[y] - get(x)[y] == 1) yield(x1 to y)
        } catch (_: IndexOutOfBoundsException) {
        }
    }
    for (dy in arrayOf(-1, 1)) {
        try {
            val y1 = y + dy
            if (get(x)[y1] - get(x)[y] == 1) yield(x to y1)
        } catch (_: IndexOutOfBoundsException) {
        }
    }
}

fun TopoMap.cell(coord: Coord): Int {
    return get(coord.first)[coord.second]
}

fun TopoMap.reachable(): ReachableMap {
    val rows = size
    val cols = get(0).size

    var reachableMap: ReachableMap = (0..<rows)
        .map { x ->
            (0..<cols).map { y ->
                if (cell(x to y) == 9) setOf(x to y) else emptySet()
            }.toMutableList()
        }.toMutableList()

    for (height in 8 downTo 0) {
        pointsOfHeight(height).forEach { (x, y) ->
            reachableMap[x][y] = neighborsOneHigher(x to y)
                .map { (nx, ny) -> reachableMap[nx][ny] }
                .fold(emptySet()) { one, two -> one + two }
        }
    }

    return reachableMap
}

object Day10 {
    fun parse(input: String): TopoMap {
        return input.lines().map { it.toCharArray().map { it.digitToInt() } }
    }

    fun part1(input: String): Int {
        val topoMap = parse(input)
        val reachableMap = topoMap.reachable()
        return topoMap.pointsOfHeight(0).map { (x, y) ->
            reachableMap[x][y].size
        }.sum()
    }

    fun part2(input: String): Int {
        return 123
    }
}