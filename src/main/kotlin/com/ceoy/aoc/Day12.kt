package com.ceoy.aoc

class Day12(private val initialState: String, rules: List<String>) {
    private val rules = rules.mapNotNull {
        Rule.parse(it)
    }

    fun partOne(): Int {
        var generation = Generation(initialState, 0)
        repeat(20) {
            generation = nextGeneration(generation)
        }

        return generation.calculateValue()
    }

    fun partTwo(): Int {
        var generation = Generation(initialState, 0)

        repeat(50000000000) {
            generation = nextGeneration(generation)
        }

        return generation.calculateValue()
    }

    /**
     * Returns the Next Generation
     */
    private fun nextGeneration(currentGeneration: Generation): Generation {

        var newTempState = currentGeneration.state
        var newStartIndex = currentGeneration.startIndex

        while (newTempState.take(5) == ".....") {
            // remove these shits :<
            newTempState = newTempState.drop(5)
            newStartIndex += 5
        }

        if (currentGeneration.state[0] == '#' ||
                currentGeneration.state[1] == '#') {

            // this might increase this hole thing a bit too much but w/e
            newTempState = "..$newTempState"
            newStartIndex -= 2
        }

        val curLength = newTempState.length
        if (newTempState[curLength - 1] == '#' ||
                newTempState[curLength - 2] == '#') {
            newTempState = "$newTempState.."
        }

        val newState = newTempState.toCharArray()

        newTempState.forEachIndexed { index, c ->
            // create string to check with the rules :)

            val default = fun(_: Int): Char {
                return '.'
            }

            val one = newTempState.getOrElse(index - 2, default)
            val two = newTempState.getOrElse(index - 1, default)
            val four = newTempState.getOrElse(index + 1, default)
            val five = newTempState.getOrElse(index + 2, default)

            val testString = "$one$two$c$four$five"

            val toReplace = if (rules.find { it.rule == testString } != null) {
                '#'
            } else {
                '.'
            }

            newState[index] = toReplace
        }


        return Generation(String(newState), newStartIndex)
    }

    data class Generation(val state: String, val startIndex: Int) {
        fun calculateValue(): Int {
            var value = 0
            state.forEachIndexed { index, char ->
                if (char == '#') value += startIndex  + index
            }

            return value
        }
    }

    data class Rule(val rule: String) {
        companion object {

            /**
             * Parse the Rule and drop it, if the result is not '#'
             */
            fun parse(input: String): Rule? {
                val rule = input.substring(0 until input.indexOf("=>")).trim()
                val result = input.substring(input.indexOf("=>") + 2 until input.length).trim()

                return if (result != "#") {
                    null
                } else {
                    Rule(rule)
                }
            }
        }
    }

    private inline fun repeat(times: Long, action: (Long) -> Unit) {
        for (index in 0 until times) {
            action(index)
        }
    }
}