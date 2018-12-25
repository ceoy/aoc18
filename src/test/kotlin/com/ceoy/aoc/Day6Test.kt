package com.ceoy.aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day6Test {

    private var testInput = listOf("1, 1",
            "1, 6",
            "8, 3",
            "3, 4",
            "5, 5",
            "8, 9")

    @Test
    fun `Day 6 Part One Example`() {
        val day6 = Day6(testInput)
        assertEquals(17, day6.one())
    }

    @Test
    fun `Day 6 Part One Actual`() {
        val day6 = Day6()
        assertEquals(3722, day6.one())
    }

    @Test
    fun `Day 6 Part Two Example`() {
        val day6 = Day6(testInput)
        assertEquals(16, day6.two(32))
    }

    @Test
    fun `Day 6 Part Two Actual`() {
       val day6 = Day6()
        assertEquals(44634, day6.two())
    }
}