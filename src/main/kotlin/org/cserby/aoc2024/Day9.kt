package org.cserby.aoc2024

data class File(val index: Int, val size: Int)

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

    private fun parse2(input: String): List<File> {
        return input.toCharArray()
            .map { it.digitToInt() }
            .foldIndexed(emptyList<File>()) { index, files, value ->
                if (index % 2 == 0) files + File(index / 2, value) else files + File(-1, value)
            }
    }

    private fun tryMove(
        fileMap: MutableList<File>,
        fileIdxToMove: Int,
    ): MutableList<File> {
        val idxOfFileToMove = fileMap.indexOfFirst { it.index == fileIdxToMove }
        val fileToMove = fileMap[idxOfFileToMove]
        val freeToUseIdx = fileMap.indexOfFirst { it.index == -1 && it.size >= fileToMove.size }
        if (freeToUseIdx != -1 && freeToUseIdx < idxOfFileToMove) {
            var freeToAdd = fileToMove.size
            if (idxOfFileToMove < fileMap.size - 1 && fileMap[idxOfFileToMove + 1].index == -1) {
                freeToAdd += fileMap[idxOfFileToMove + 1].size
                fileMap.removeAt(idxOfFileToMove + 1)
            }
            fileMap.set(idxOfFileToMove, File(-1, freeToAdd))
            if (idxOfFileToMove > 0 && fileMap[idxOfFileToMove - 1].index == -1) {
                freeToAdd += fileMap[idxOfFileToMove - 1].size
                fileMap.set(idxOfFileToMove, File(-1, freeToAdd))
                fileMap.removeAt(idxOfFileToMove - 1)
            }
            val freeLeft = fileMap[freeToUseIdx].size - fileToMove.size
            if (freeLeft > 0) {
                fileMap.set(freeToUseIdx, File(-1, freeLeft))
            } else {
                fileMap.removeAt(freeToUseIdx)
            }
            fileMap.add(freeToUseIdx, fileToMove)
            return fileMap
        } else {
            return fileMap
        }
    }

    private fun defragged2(fileMap: List<File>): List<File> {
        val fileCount = fileMap.count { it.index != -1 }
        var mutableFileMap = fileMap.toMutableList()

        for (fileIdxToMove in fileCount - 1 downTo 0) {
            mutableFileMap = tryMove(mutableFileMap, fileIdxToMove)
        }
        return mutableFileMap
    }

    private fun chunks(fileMap: List<File>): Sequence<Int> =
        sequence {
            for (file in fileMap) {
                yieldAll(fileContents(if (file.index == -1) 0 else file.index, file.size))
            }
        }

    fun part2(input: String): Long {
        val fileMap = parse2(input)

        return chunks(defragged2(fileMap)).foldIndexed(0L) {
                index, acc, value ->
            acc + index * value
        }
    }
}
