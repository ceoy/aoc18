package com.ceoy.aoc

import java.lang.IllegalStateException

class Day2 {

    private val input = FileLoader.load("day2.txt")

    fun one() {
        var two = 0
        var threes = 0
        input.forEach {


            val countList = mutableMapOf<Char, Int>()
            it.forEach { char ->
                // increase by one
                countList[char] = countList.getOrPut(char) { 0 } + 1
            }
            if (countList.containsValue(2)) two++
            if (countList.containsValue(3)) threes++
        }

        println(two * threes)
    }

    fun two() {
        input.forEach { first ->
            val charArrayOne = first.toCharArray()

            input.forEach { second ->
                val charArrayTwo = second.toCharArray()
                var differenceFound = 0
                var difference = -1
                charArrayOne.forEachIndexed { index, char ->
                    if (charArrayTwo[index] != char) {
                        differenceFound++
                        difference = index
                    }
                }

                if (differenceFound == 1) {
                    val result = first.replaceRange(difference..difference, "")
                    println(result)
                    return
                }
            }
        }

        throw IllegalStateException("Nothing found")
    }

}