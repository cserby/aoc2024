package org.cserby.aoc2024

object Day24 {
    data class Instruction(val op1: String, val operand: (Boolean, Boolean) -> Boolean, val op2: String, val res: String)

    data class Gadget(val values: Map<String, Boolean>, val instructions: List<Instruction>) {
        fun solve(): Map<String, Boolean> {
            val vals = values.toMutableMap()
            val instrs = instructions.toMutableList()

            while (instrs.isNotEmpty()) {
                val instruction = instrs.take(1)[0]
                try {
                    vals[instruction.res] = instruction.operand(vals[instruction.op1]!!, vals[instruction.op2]!!)
                    instrs.remove(instruction)
                } catch (_: NullPointerException) {
                    instrs.remove(instruction)
                    instrs.add(instruction)
                }
            }

            return vals
        }
    }

    fun parse(input: String): Gadget {
        val (valuesInput, instructionsInput) = input.split("\n\n")
        return Gadget(
            values =
                valuesInput.lines().map { v ->
                    v.split(": ").let { vs ->
                        vs[0] to (vs[1] == "1")
                    }
                }.toMap(),
            instructions =
                instructionsInput.lines().map { i ->
                    val (opPart, res) = i.split(" -> ")
                    val (op1, op, op2) = opPart.split(" ")
                    Instruction(
                        op1 = op1,
                        operand =
                            when (op) {
                                "AND" -> { o1: Boolean, o2: Boolean -> o1.and(o2) }
                                "OR" -> { o1: Boolean, o2: Boolean -> o1.or(o2) }
                                "XOR" -> { o1: Boolean, o2: Boolean -> o1.xor(o2) }
                                else -> throw Exception("Unknown operand $op")
                            },
                        op2 = op2,
                        res = res,
                    )
                },
        )
    }

    fun part1(input: String): Long {
        val gadget = parse(input)
        val solution = gadget.solve()
        val output =
            solution
                .filter { it.key.startsWith("z") }
                .entries.sortedBy { it.key }.reversed()
                .map {
                    if (it.value) "1" else "0"
                }.joinToString("")
                .toLong(2)

        return output
    }
}
