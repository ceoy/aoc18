package com.ceoy.aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day16Test {
    private val actualInput = FileLoader.load("day16_one.txt")
    private val programInput = FileLoader.load("day16_two.txt")

    @Test
    fun `Day 16 Part One`() {
        val day16 = Day16(actualInput)
        assertEquals(605, day16.partOne())
    }

    @Test
    fun `Day 16 Part Two`() {
        val day16 = Day16(actualInput, programInput)
        assertEquals(653, day16.partTwo())
    }
}