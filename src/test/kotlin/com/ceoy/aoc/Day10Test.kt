package com.ceoy.aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day10Test {

    private val exampleInput = FileLoader.load("day10_example.txt")

    @Test
    fun `Day 10 Part One Example`() {
        val day10 = Day10(exampleInput)
        day10.partOneAndTwo("partOneExample")
    }

    @Test
    fun `Day 10 Part One Actual`() {
        val day10 = Day10()
        day10.partOneAndTwo("partOneActual")
    }

    @Test
    fun `Day 10 Part Two Example`() {
        val day10 = Day10(exampleInput)
        assertEquals(3, day10.partOneAndTwo("partTwoExample"))
    }

    @Test
    fun `Day 10 Part Two Actual`() {
        val day10 = Day10()
        assertEquals(10521, day10.partOneAndTwo("partTwoActual"))
    }
}