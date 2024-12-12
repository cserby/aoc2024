package org.cserby.aoc2024.day12

typealias Matrix<T> = List<List<T>>

typealias FieldMap = Matrix<Plant>

typealias Coords = Pair<Int, Int>

typealias Plant = Char

fun <T> Matrix<T>.cell(coords: Coords): T {
    return get(coords.first)[coords.second]
}

fun <T> Matrix<T>.safeCell(coords: Coords): T? {
    return runCatching { cell(coords) }.getOrNull()
}

fun <T> Matrix<T>.neighbors(coords: Coords): Sequence<Coords> =
    sequence {
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

fun <T> Matrix<T>.domainFrom(
    startCoords: Coords,
    onVisit: (visitedCoords: Coords) -> Unit,
): Set<Coords> {
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

fun <T> Matrix<T>.domains(): Sequence<Set<Coords>> =
    sequence {
        val visited: MutableList<MutableList<Boolean>> = copy { false }
        while (true) {
            val nextUnvisited = (visited as Matrix<Boolean>).firstCoordWhere { it == false }
            if (nextUnvisited == null) break
            yield(domainFrom(nextUnvisited) { visited[it.first][it.second] = true })
        }
    }

private fun <T> Matrix<T>.domainPrice(coords: Set<Coords>): Int {
    return coords.size *
        coords.fold(0) { acc, coord ->
            acc + 4 - neighborValues(coord).filter { it == cell(coord) }.count()
        }
}

fun <T> Matrix<T>.domainPrices(): Sequence<Int> = domains().map { domainPrice(it) }

fun <T> Matrix<T>.horizontalFencesToTheRight(): Sequence<Pair<Coords, Int>> =
    sequence {
        for (x in 0..<size) {
            var previousToRightValue: T? = safeCell(x to 0)
            var previousToLeftValue: T? = safeCell(x - 1 to 0)

            var startToRight: Coords = x to 0

            var fenceToRight: Boolean = previousToLeftValue != previousToRightValue

            for (y in 1..get(0).size) {
                var currToRight = x to y
                var currToRightValue = safeCell(currToRight)
                var currToLeft = x - 1 to y
                var currToLeftValue = safeCell(currToLeft)

                if (!fenceToRight) {
                    if (currToRightValue != currToLeftValue) {
                        fenceToRight = true
                        startToRight = currToRight
                    }
                } else { // fenceToRight
                    if (currToRightValue == currToLeftValue) { // fence stop
                        fenceToRight = false
                        yield(startToRight to y - startToRight.second)
                    } else if (currToRightValue != previousToRightValue) {
                        yield(startToRight to y - startToRight.second)
                        startToRight = currToRight
                    }
                }

                previousToRightValue = currToRightValue
                previousToLeftValue = currToLeftValue
            }
        }
    }

fun <T> Matrix<T>.rotateRight(): Matrix<T> {
    return indices.map { x -> get(x).indices.map { y -> cell(size - y - 1 to x) } }
}

fun <T> Matrix<T>.rotateLeftCoord(coords: Coords): Coords {
    return size - coords.second - 1 to coords.first
}

object Day12 {
    private fun parse(input: String): FieldMap {
        return input.lines().map { it.toCharArray().toList() }
    }

    fun part1(input: String): Int {
        return parse(input).domainPrices().fold(0) { acc, it -> acc + it }
    }

    fun part2(input: String): Int {
        val field = parse(input)
        val domains = field.domains().toList()
        val horizontalFencesToRight = field.horizontalFencesToTheRight()
        val verticalFencesToRight = field.rotateRight().horizontalFencesToTheRight().map { field.rotateLeftCoord(it.first) to it.second }
        val horizontalFencesToLeft =
            field.rotateRight().rotateRight().horizontalFencesToTheRight()
                .map { field.rotateLeftCoord(it.first) to it.second }
                .map { field.rotateLeftCoord(it.first) to it.second }
        val verticalFencesToLeft =
            field.rotateRight().rotateRight().rotateRight().horizontalFencesToTheRight()
                .map { field.rotateLeftCoord(it.first) to it.second }
                .map { field.rotateLeftCoord(it.first) to it.second }
                .map { field.rotateLeftCoord(it.first) to it.second }
        val domainHorizontalRightFenceCount: Map<Set<Coords>, Int> =
            horizontalFencesToRight.groupingBy {
                    fence ->
                domains.find { it.contains(fence.first) }!!
            }.eachCount()
        val domainVerticalRightFenceCount: Map<Set<Coords>, Int> =
            verticalFencesToRight.groupingBy {
                    fence ->
                domains.find { it.contains(fence.first) }!!
            }.eachCount()
        val domainHorizontalLeftFenceCount: Map<Set<Coords>, Int> =
            horizontalFencesToLeft.groupingBy {
                    fence ->
                domains.find { it.contains(fence.first) }!!
            }.eachCount()
        val domainVerticalLeftFenceCount: Map<Set<Coords>, Int> =
            verticalFencesToLeft.groupingBy {
                    fence ->
                domains.find { it.contains(fence.first) }!!
            }.eachCount()
        return domainVerticalRightFenceCount.entries.fold(0) { acc, (key, value) -> acc + key.size * value } +
            domainHorizontalLeftFenceCount.entries.fold(0) { acc, (key, value) -> acc + key.size * value } +
            domainVerticalLeftFenceCount.entries.fold(0) { acc, (key, value) -> acc + key.size * value } +
            domainHorizontalRightFenceCount.entries.fold(0) { acc, (key, value) -> acc + key.size * value }
    }
}
