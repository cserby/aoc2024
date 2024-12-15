package org.cserby.aoc2024

object Utils {
    enum class Direction(dir: Char) {
        UP('^'),
        RIGHT('>'),
        DOWN('v'),
        LEFT('<'),
        ;

        companion object {
            fun fromChar(dir: Char): Direction {
                return when (dir) {
                    '^' -> UP
                    '>' -> RIGHT
                    'v' -> DOWN
                    '<' -> LEFT
                    else -> throw IllegalArgumentException("'$dir' is not a Direction")
                }
            }
        }
    }
}
