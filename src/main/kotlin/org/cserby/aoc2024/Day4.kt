package org.cserby.aoc2024

typealias Matrix = List<CharArray>

private fun Matrix.matrixSequence(
    startX: Int,
    startY: Int,
    includeStart: Boolean = true,
    stepX: Int,
    stepY: Int
): Sequence<Char> = sequence {
    var currX = startX
    var currY = startY

    try {
        if (includeStart) {
            yield(get(currX).get(currY))
        }
        while(true) {
            currX += stepX
            currY += stepY
            yield(get(currX).get(currY))
        }
    } catch (_: IndexOutOfBoundsException) {
        return@sequence
    }
}

private fun Matrix.leftFrom(startX: Int, startY: Int, includeStart: Boolean = true): Sequence<Char> = sequence {
    yieldAll(matrixSequence(startX, startY, includeStart, stepX = 0, stepY = -1))
}

private fun Matrix.rightFrom(startX: Int, startY: Int, includeStart: Boolean = true): Sequence<Char> = sequence {
    yieldAll(matrixSequence(startX, startY, includeStart, stepX = 0, stepY = 1))
}

private fun Matrix.upFrom(startX: Int, startY: Int, includeStart: Boolean = true): Sequence<Char> = sequence {
    yieldAll(matrixSequence(startX, startY, includeStart, stepX = -1, stepY = 0))
}

private fun Matrix.downFrom(startX: Int, startY: Int, includeStart: Boolean = true): Sequence<Char> = sequence {
    yieldAll(matrixSequence(startX, startY, includeStart, stepX = 1, stepY = 0))
}

private fun Matrix.upLeftFrom(startX: Int, startY: Int, includeStart: Boolean = true): Sequence<Char> = sequence {
    yieldAll(matrixSequence(startX, startY, includeStart, stepX = -1, stepY = -1))
}

private fun Matrix.upRightFrom(startX: Int, startY: Int, includeStart: Boolean = true): Sequence<Char> = sequence {
    yieldAll(matrixSequence(startX, startY, includeStart, stepX = -1, stepY = 1))
}

private fun Matrix.downLeftFrom(startX: Int, startY: Int, includeStart: Boolean = true): Sequence<Char> = sequence {
    yieldAll(matrixSequence(startX, startY, includeStart, stepX = 1, stepY = -1))
}

private fun Matrix.downRightFrom(startX: Int, startY: Int, includeStart: Boolean = true): Sequence<Char> = sequence {
    yieldAll(matrixSequence(startX, startY, includeStart, stepX = 1, stepY = 1))
}

fun Matrix.allCells(): Sequence<Pair<Int, Int>> = sequence {
    for (x in 0..<size) {
        for (y in 0..<get(x).size) {
            yield(x to y)
        }
    }
}

private fun isXmas(seq: Sequence<Char>): Boolean {
    return seq.take(4).joinToString("") == "XMAS"
}

private fun Matrix.countXmasFrom(startX: Int, startY: Int): Int {
    if (get(startX).get(startY) != 'X') return 0
    return (
            (if(isXmas(upFrom(startX, startY))) 1 else 0)
            + (if(isXmas(upLeftFrom(startX, startY))) 1 else 0)
            + (if(isXmas(leftFrom(startX, startY))) 1 else 0)
            + (if(isXmas(downLeftFrom(startX, startY))) 1 else 0)
            + (if(isXmas(downFrom(startX, startY))) 1 else 0)
            + (if(isXmas(downRightFrom(startX, startY))) 1 else 0)
            + (if(isXmas(rightFrom(startX, startY))) 1 else 0)
            + (if(isXmas(upRightFrom(startX, startY))) 1 else 0)
            )
}

fun Matrix.countXmases(): Int {
    val xmases = allCells().map{ (currX, currY) -> countXmasFrom(currX, currY) }
    return xmases.sum()
}

private fun Matrix.isCrossMas(startX: Int, startY: Int): Boolean {
    if(get(startX).get(startY) != 'A') return false
    try {
        return setOf(get(startX - 1).get(startY - 1), get(startX + 1).get(startY + 1)) == setOf('M', 'S')
                && setOf(get(startX + 1).get(startY - 1), get(startX - 1).get(startY + 1)) == setOf('M', 'S')
    } catch (_: IndexOutOfBoundsException) {
        return false
    }
}

fun Matrix.countCrossMases(): Int {
    return allCells().map{ (currX, currY) -> if(isCrossMas(currX, currY)) 1 else 0 }.sum()
}

object Day4 {
    private fun parse(input: String): Matrix {
        return input.lines().map { it.toCharArray() }
    }


    fun part1(input: String): Int {
        return parse(input).countXmases()
    }

    fun part2(input: String): Int {
        return parse(input).countCrossMases()
    }
}