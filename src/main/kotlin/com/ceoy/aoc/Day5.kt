package com.ceoy.aoc

class Day5 {
    private val input = FileLoader.load("day5.txt").first()

    fun one() {
        // println(polymer(input).count())
    }

    private fun polymer(input: String): String {
        var str = input
        var changed = true
        while (changed) {
            var brk = false
            val length = str.length
            for (i in 0 until length) {
                val firstLower = str[i].isLowerCase()
                val firstLetter = str[i]
                if (i + 1 < str.length) {
                    val secondLower = str[i + 1].isLowerCase()
                    val secondLetter = str[i + 1]

                    if (secondLower != firstLower && firstLetter.equals(secondLetter, true)) {
                        str = str.removeRange(i, i + 2)

                        brk = true
                        break
                    }
                }
            }
            if (!brk) {
                changed = false
            }
        }

        return str
    }

    fun two() {
        val map = mutableMapOf<Char, Int>()
        for (c in 'A'..'Z') {
            map[c] = doForChar(input, c).count()
        }

        val value = map.minBy {
            it.value
        }?.value ?: -1

        println(value)
    }

    private fun doForChar(input: String, char: Char): String {
        val str = input.filter {
            it != char && it != char.toLowerCase()
        }
        return polymer(str)
    }
}