package com.ceoy.aoc

import org.junit.Test

class Day17Test {

    private val actualInput = FileLoader.load("day17.txt")

    @Test
    fun `Day 17 Part One Actual`() {
        val day17 = Day17(actualInput)
        println(day17.partOne())
    }
}