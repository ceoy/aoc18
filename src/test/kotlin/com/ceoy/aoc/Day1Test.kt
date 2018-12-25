package com.ceoy.aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day1Test {

    @Test
    fun `Day 1 Part One Example`() {
        val testInput = listOf("+1", "+1", "+1")
        val day1 = Day1(testInput)
        assertEquals(3, day1.one())

        val testInput2 = listOf("+1", "+1", "-2")
        val day1Two = Day1(testInput2)
        assertEquals(0, day1Two.one())
    }

    @Test
    fun `Day 1 Part One Actual`() {
        val day1 = Day1()
        assertEquals(427, day1.one())
    }

    @Test
    fun `Day 1 Part Two Example`() {
        val testInput = listOf("+1", "-1")
        val day1 = Day1(testInput)
        assertEquals(0, day1.two())

        val testInput2 = listOf("+3", "+3", "+4", "-2", "-4")
        val day1Two = Day1(testInput2)
        assertEquals(10, day1Two.two())
    }

    @Test
    fun `Day 1 Part Two Actual`() {
        val day1 = Day1()
        assertEquals(341, day1.two())
    }
}