package com.ceoy.aoc

class Day16(
    private val input: List<String>,
    private val program: List<String>? = null
) {

    private val allInstructions = mutableListOf(
        addr, addi, mulr, muli, banr, bani, borr, bori, setr, seti, gtir, gtii, gtrr, eqir, eqii, eqrr
    )

    fun partOne(): Int {

        // step one: figure out op-code number
        // figure out
        return input.chunked(4).count { input ->
            val registers = input[0].filter { it.isDigit() }.map(Character::getNumericValue).toIntArray()

            val resultRegisters =
                input[2].filter { it.isDigit() }.map(Character::getNumericValue).toIntArray()

            val instructionInfo =
                input[1].split(" ").map(String::toInt).toIntArray()

            val validInstructions = allInstructions.count { instruction ->
                val registerCopy = registers.copyOf()
                instruction(registerCopy, instructionInfo[1], instructionInfo[2], instructionInfo[3])
                registerCopy.contentEquals(resultRegisters)
            }

            validInstructions >= 3
        }
    }

    fun partTwo(): Int {
        val opCodes = mutableMapOf<Int, (IntArray, Int, Int, Int) -> Unit>()

        // step one: figure out op-code numb
        while (allInstructions.size != 0) {
            input.chunked(4).forEach { input ->

                val registers = input[0].filter { it.isDigit() }.map(Character::getNumericValue).toIntArray()

                val resultRegisters = input[2]
                    .filter { it.isDigit() }
                    .map(Character::getNumericValue)
                    .toIntArray()

                val instructionInfo = input[1]
                    .split(" ")
                    .map(String::toInt)
                    .toIntArray()

                if (opCodes.containsKey(instructionInfo[0])) return@forEach

                val possibilities = allInstructions.mapNotNull { instruction ->
                    val registerCopy = registers.copyOf()
                    instruction(registerCopy, instructionInfo[1], instructionInfo[2], instructionInfo[3])
                    if (registerCopy.contentEquals(resultRegisters)) {
                        instruction
                    } else {
                        null
                    }
                }

                if (possibilities.size == 1) {
                    possibilities.first().run {
                        opCodes[instructionInfo[0]] = this
                        allInstructions.remove(this)
                    }
                }
            }
        }

        val register = IntArray(4) { 0 }

        program?.forEach { programLine ->

            val instructionInfo = programLine
                .split(" ")
                .map(String::toInt)
                .toIntArray()

            val instruction = opCodes[instructionInfo[0]]!!
            instruction(register, instructionInfo[1], instructionInfo[2], instructionInfo[3])
        }

        return register[0]
    }

    companion object {

        private val addr: (IntArray, Int, Int, Int) -> Unit = { registers, a, b, c ->
            registers[c] = registers[a] + registers[b]
        }

        private val addi: (IntArray, Int, Int, Int) -> Unit = { registers, a, valB, c ->
            registers[c] = registers[a] + valB
        }

        private val mulr: (IntArray, Int, Int, Int) -> Unit = { registers, a, b, c ->
            registers[c] = registers[a] * registers[b]
        }

        private val muli: (IntArray, Int, Int, Int) -> Unit = { registers, a, valB, c ->
            registers[c] = registers[a] * valB
        }

        private val banr: (IntArray, Int, Int, Int) -> Unit = { registers, a, b, c ->
            registers[c] = registers[a] and registers[b]
        }

        private val bani: (IntArray, Int, Int, Int) -> Unit = { registers, a, valB, c ->
            registers[c] = registers[a] and valB
        }

        private val borr: (IntArray, Int, Int, Int) -> Unit = { registers, a, b, c ->
            registers[c] = registers[a] or registers[b]
        }

        private val bori: (IntArray, Int, Int, Int) -> Unit = { registers, a, valB, c ->
            registers[c] = registers[a] or valB
        }

        private val setr: (IntArray, Int, Int, Int) -> Unit = { registers, a, _, c ->
            registers[c] = registers[a]
        }

        private val seti: (IntArray, Int, Int, Int) -> Unit = { registers, valA, _, c ->
            registers[c] = valA
        }

        private val gtir: (IntArray, Int, Int, Int) -> Unit = { registers, valA, b, c ->
            registers[c] = if (valA > registers[b]) 1 else 0
        }

        private val gtii: (IntArray, Int, Int, Int) -> Unit = { registers, a, valB, c ->
            registers[c] = if (registers[a] > valB) 1 else 0
        }

        private val gtrr: (IntArray, Int, Int, Int) -> Unit = { registers, a, b, c ->
            registers[c] = if (registers[a] > registers[b]) 1 else 0
        }

        private val eqir: (IntArray, Int, Int, Int) -> Unit = { registers, valA, b, c ->
            registers[c] = if (valA == registers[b]) 1 else 0
        }

        private val eqii: (IntArray, Int, Int, Int) -> Unit = { registers, a, valB, c ->
            registers[c] = if (registers[a] == valB) 1 else 0
        }

        private val eqrr: (IntArray, Int, Int, Int) -> Unit = { registers, a, b, c ->
            registers[c] = if (registers[a] == registers[b]) 1 else 0
        }
    }
}