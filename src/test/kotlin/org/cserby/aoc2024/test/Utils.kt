package org.cserby.aoc2024.test

object Utils {
    fun readFile(resourcePath: String): String {
        return javaClass.getResource(resourcePath)?.readText()!!.trim();
    }
}
