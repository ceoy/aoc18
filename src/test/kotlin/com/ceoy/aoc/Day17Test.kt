package com.ceoy.aoc

import org.junit.Test

class Day17Test {

    private val actualInput = FileLoader.load("day17.txt")
    private val exampleInput = FileLoader.load("day17_example.txt")

    @Test
    fun `Day 17 Part One Actual`() {
        val day17 = Day17(actualInput)
        println(day17.partOne())
    }

    @Test
    fun `Day 17 Part One Example`() {
        val day17 = Day17(exampleInput)
        day17.partOne()
    }
}