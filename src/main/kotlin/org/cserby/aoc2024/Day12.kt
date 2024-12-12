package org.cserby.aoc2024.day12

typealias Matrix<T> = List<List<T>>

typealias FieldMap = Matrix<Plant>

typealias Coords = Pair<Int, Int>

typealias Plant = Char

fun <T> Matrix<T>.cell(coords: Coords): T {
    return get(coords.first)[coords.second]
}

fun <T> Matrix<T>.neighbors(coords: Coords): Sequence<Coords> = sequence {
    val (x, y) = coords
    for (dx in arrayOf(-1, 1)) {
        try {
            val x1 = x + dx
            cell(x1 to y)
            yield(x1 to y)
        } catch (_: IndexOutOfBoundsException) {
        }
    }
    for (dy in arrayOf(-1, 1)) {
        try {
            val y1 = y + dy
            cell(x to y1)
            yield(x to y1)
        } catch (_: IndexOutOfBoundsException) {
        }
    }
}

fun <T> Matrix<T>.neighborValues(coords: Coords): Sequence<T> = neighbors(coords).map { cell(it) }

fun <T, R> Matrix<T>.copy(init: (T) -> R): MutableList<MutableList<R>> {
    return map { row -> row.map { init(it) }.toMutableList() }.toMutableList()
}

fun <T> Matrix<T>.firstCoordWhere(check: (T) -> Boolean): Coords? {
    return indices.flatMap { x ->
        this[x].indices.map { y -> x to y }
    }.firstOrNull { coords -> check(cell(coords)) }
}

fun <T> Matrix<T>.domainFrom(startCoords: Coords, onVisit: (visitedCoords: Coords) -> Unit): Set<Coords> {
    var toVisit = mutableSetOf<Coords>(startCoords)
    var visited = mutableSetOf<Coords>()

    while (toVisit.isNotEmpty()) {
        val (visitedNow) = toVisit.take(1)
        toVisit.remove(visitedNow)
        val visitedNowValue = cell(visitedNow)
        if (visited.contains(visitedNow)) continue
        onVisit(visitedNow)
        visited.add(visitedNow)
        toVisit.addAll(neighbors(visitedNow).filter { cell(it) == visitedNowValue })
    }

    return visited
}

fun <T> Matrix<T>.domains(): Sequence<Set<Coords>> = sequence {
    val visited: MutableList<MutableList<Boolean>> = copy { false }
    while (true) {
        val nextUnvisited = (visited as Matrix<Boolean>).firstCoordWhere { it == false }
        if (nextUnvisited == null) break
        yield(domainFrom(nextUnvisited, { visited[it.first][it.second] = true }))
    }
}

private fun <T> Matrix<T>.domainPrice(coords: Set<Coords>): Int {
    return coords.size * coords.fold(0) { acc, coord ->
        acc + 4 - neighborValues(coord).filter { it == cell(coord) }.count()
    }
}

fun <T> Matrix<T>.domainPrices(): Sequence<Int> = domains().map { domainPrice(it) }

object Day12 {
    private fun parse(input: String): FieldMap {
        return input.lines().map { it.toCharArray().toList() }
    }


    fun part1(input: String): Int {
        return parse(input).domainPrices().fold(0) { acc, it -> acc + it }

    }

    fun part2(input: String): Int {
        return 32
    }
}