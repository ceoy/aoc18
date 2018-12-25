package com.ceoy.aoc

class Day1(input: List<String> = FileLoader.load("day1.txt")) {

    private val input: List<Int> = input.map { it.toInt() }

    private val frequencies = mutableListOf(0)

    fun one(): Int = input.sum()

    fun two(): Int = findFirstDuplicateFrequency()

    private fun findFirstDuplicateFrequency(): Int {
        input.forEach {
            val newElementToAdd = if (frequencies.isEmpty()) {
                it
            } else {
                frequencies.last() + it
            }

            if (frequencies.contains(newElementToAdd)) {
                return newElementToAdd
            }

            frequencies.add(newElementToAdd)
        }

        return findFirstDuplicateFrequency()
    }
}