package org.cserby.aoc2024

typealias Measurement = List<Int>

object Day2 {
    private fun parse(input: String): List<Measurement> {
        return input.lines().map(Day2::parseMeasurement)
    }

    private fun parseMeasurement(line: String): Measurement {
        return line.split(" ").map(String::toInt)
    }

    private fun isSafelyIncreasing(measurement: Measurement): Boolean {
        return measurement.zipWithNext().fold(true) {
            safeSoFar, currentPair ->
                safeSoFar &&
                        currentPair.second - currentPair.first >= 1 &&
                        currentPair.second - currentPair.first <= 3
        }
    }

    private fun isMeasurementSafe(measurement: Measurement): Boolean {
        return isSafelyIncreasing(measurement) || isSafelyIncreasing(measurement.reversed())
    }

    fun part1(input: String): Int {
        return parse(input).count(Day2::isMeasurementSafe)
    }
}
