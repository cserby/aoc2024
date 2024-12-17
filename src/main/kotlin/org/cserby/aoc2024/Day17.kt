package org.cserby.aoc2024

import kotlin.math.floor
import kotlin.math.pow

object Day17 {
    class ComputerHaltException(val comp: Computer) : Exception("Computer halted")

    data class Computer(
        val registerA: Long,
        val registerB: Long,
        val registerC: Long,
        val program: List<Int>,
        val instructionPointer: Int,
        val output: List<Int>,
    ) {
        private fun comboOperand(): Long {
            val operand = program[instructionPointer + 1]
            return when (operand) {
                0 -> 0
                1 -> 1
                2 -> 2
                3 -> 3
                4 -> registerA
                5 -> registerB
                6 -> registerC
                else -> throw Exception("Invalid combo operand at ${instructionPointer + 1}: $operand")
            }
        }

        private fun literalOperand(): Long {
            return program[instructionPointer + 1].toLong()
        }

        private fun adv(wr: (Long, Computer) -> Computer = { result, comp -> comp.copy(registerA = result) }): Computer {
            val numerator = registerA
            val operand = comboOperand().toDouble()
            val result = floor(numerator / 2.0.pow(operand)).toLong()
            return wr(result, this).copy(
                instructionPointer = instructionPointer + 2,
            )
        }

        private fun bxl(): Computer {
            return copy(
                registerB = registerB.xor(literalOperand()),
                instructionPointer = instructionPointer + 2,
            )
        }

        private fun bst(): Computer {
            return copy(
                registerB = comboOperand().mod(8).toLong(),
                instructionPointer = instructionPointer + 2,
            )
        }

        private fun jnz(): Computer {
            return if (registerA == 0L) {
                copy(instructionPointer = instructionPointer + 2)
            } else {
                copy(instructionPointer = literalOperand().toInt())
            }
        }

        private fun bxc(): Computer {
            return copy(
                registerB = registerB.xor(registerC),
                instructionPointer = instructionPointer + 2,
            )
        }

        private fun out(): Computer {
            return copy(
                output = output + comboOperand().mod(8),
                instructionPointer = instructionPointer + 2,
            )
        }

        private fun bdv(): Computer {
            return adv { result, comp -> comp.copy(registerB = result) }
        }

        private fun cdv(): Computer {
            return adv { result, comp -> comp.copy(registerC = result) }
        }

        fun step(): Computer {
            return runCatching {
                val opcode = program[instructionPointer]
                return when (opcode) {
                    0 -> adv()
                    1 -> bxl()
                    2 -> bst()
                    3 -> jnz()
                    4 -> bxc()
                    5 -> out()
                    6 -> bdv()
                    7 -> cdv()
                    else -> throw Exception("Invalid opcode $opcode")
                }
            }.getOrElse { e ->
                when (e) {
                    is IndexOutOfBoundsException -> throw ComputerHaltException(this)
                    else -> throw e
                }
            }
        }

        fun steps(): Sequence<Computer> =
            sequence {
                var curr = this@Computer
                while (true) {
                    try {
                        curr = curr.step()
                        yield(curr)
                    } catch (c: ComputerHaltException) {
                        return@sequence
                    }
                }
            }
    }

    private fun parse(input: String): Computer {
        return input.lines().let { lines ->
            Computer(
                registerA = lines[0].substringAfter("Register A: ").toLong(),
                registerB = lines[1].substringAfter("Register B: ").toLong(),
                registerC = lines[2].substringAfter("Register C: ").toLong(),
                program = lines[4].substringAfter("Program: ").split(",").map { it.toInt() },
                instructionPointer = 0,
                output = emptyList(),
            )
        }
    }

    fun part1(input: String): String {
        return parse(input).steps().last().output.joinToString(",")
    }
}
