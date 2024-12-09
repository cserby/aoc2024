package org.cserby.aoc2024

object Day9 {
    private fun parse(input: String): Pair<List<Int>, List<Int>> {
        return input.toCharArray()
            .map { it.digitToInt() }
            .foldIndexed(emptyList<Int>() to emptyList<Int>()) { index, (files, frees), value ->
                if (index % 2 == 0) (files + value) to frees else files to (frees + value)
            }
    }

    private fun fileContents(
        fileIndex: Int,
        fileSize: Int,
    ): Sequence<Int> =
        sequence {
            for (chunk in 0..<fileSize) yield(fileIndex)
        }

    private fun fileChunksFromBackwards(files: List<Int>): Sequence<Int> =
        sequence {
            for (fileIndex in files.size - 1 downTo 0) {
                for (digit in 0..<files[fileIndex]) {
                    yield(fileIndex)
                }
            }
        }

    private fun defragged(fileMap: Pair<List<Int>, List<Int>>): Sequence<Int> =
        sequence {
            val (files, frees) = fileMap
            var fileChunksFromBackwardsSequence = fileChunksFromBackwards(files)
            for (fileIndex in 0..<files.size) {
                yieldAll(fileContents(fileIndex, files[fileIndex]))
                yieldAll(fileChunksFromBackwardsSequence.take(frees[fileIndex]))
                fileChunksFromBackwardsSequence = fileChunksFromBackwardsSequence.drop(frees[fileIndex])
            }
        }

    private fun checksum(fileMap: Pair<List<Int>, List<Int>>): Long {
        return defragged(fileMap).take(fileMap.first.sum()).foldIndexed(0L) { index, acc, value ->
            acc + index * value
        }
    }

    fun part1(input: String): Long {
        val fileMap = parse(input)
        return checksum(fileMap)
    }

    fun part2(input: String): Long {
        return 43
    }
}
