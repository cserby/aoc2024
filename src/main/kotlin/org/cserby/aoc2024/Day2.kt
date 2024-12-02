package org.cserby.aoc2024

typealias Measurement = List<Int>

object Day2 {
    private fun parse(input: String): List<Measurement> {
        return input.lines().map(Day2::parseMeasurement)
    }

    private fun parseMeasurement(line: String): Measurement {
        return line.split(" ").map(String::toInt)
    }

    private fun checkDiff(meas1: Int, meas2: Int): Boolean {
        val diff = meas2 - meas1
        return diff >= 1 && diff <= 3
    }

    private fun isSafelyIncreasing(measurement: Measurement): Boolean {
        return measurement.zipWithNext().fold(true) {
            safeSoFar, currentPair ->
                safeSoFar && checkDiff(currentPair.first, currentPair.second)
        }
    }

    private fun isMeasurementSafe(measurement: Measurement): Boolean {
        return isSafelyIncreasing(measurement) || isSafelyIncreasing(measurement.reversed())
    }

    fun part1(input: String): Int {
        return parse(input).count(Day2::isMeasurementSafe)
    }

    private fun <T>removeIndex(list: List<T>, index: Int): List<T> {
        return list.filterIndexed { idx, _ -> index != idx }
    }

    private fun increasingWithSkip(measurement: Measurement): Boolean {
        if (isSafelyIncreasing(measurement)) return true
        for (i in 0..measurement.size) {
            if (isSafelyIncreasing(removeIndex(measurement, i))) return true
        }
        return false
    }

    private fun safeWithSkip(measurement: Measurement): Boolean {
        return increasingWithSkip(measurement) || increasingWithSkip(measurement.reversed())
    }

    fun part2(input: String): Int {
        return parse(input).filter(Day2::safeWithSkip).count()
    }
}
