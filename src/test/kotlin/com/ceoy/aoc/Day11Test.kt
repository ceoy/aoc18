package com.ceoy.aoc

import org.junit.Test
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals

class Day11Test {

    private val actualInput = 6548

    @Test
    fun `Test Day 11 Example Part One`() {
        val day11 = Day11(18)
        assertEquals("33,45", day11.partOne())

        val day11Two = Day11(42)
        assertEquals("21,61", day11Two.partOne())
    }

    @Test
    fun `Day 11 Actual Part One`() {
        val day11 = Day11(actualInput)
        assertEquals("21,53", day11.partOne())
    }

    @Test
    fun `Test Power Level`() {
        assertEquals(-5, Day11.getPowerLevel(122, 79, 57))
        assertEquals(0, Day11.getPowerLevel(217, 196, 39))
        assertEquals(4, Day11.getPowerLevel(101, 153, 71))
    }

    @Test
    fun `Day 11 Part Two Example`() {
        val day11 = Day11(18)
        assertEquals("90,269,16", day11.partTwo())
    }

    @Test
    fun `Day 11 Part Two Example Two`() {
        val day11Two = Day11(42)
        assertEquals("232,251,12", day11Two.partTwo())
    }

    @Test
    fun `Day 11 Part Two Actual`() {
        val elapsedTime = measureTimeMillis {
            val day11 = Day11(actualInput)
            assertEquals("233,250,12", day11.partTwo())
        }

        println("elapsedTime: $elapsedTime")
    }
}