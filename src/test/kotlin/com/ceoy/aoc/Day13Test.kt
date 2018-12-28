package com.ceoy.aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day13Test {

    private val testDataOne = FileLoader.load("day13_example.txt")
    private val testDataTwo = FileLoader.load("day13_example_part_two.txt")
    private val actualData = FileLoader.load("day13_actual.txt")

    @Test
    fun `Day 13 Part One Test Example`() {
        val classToTest = Day13(testDataOne)
        assertEquals("7,3", classToTest.partOne())
    }

    @Test
    fun `Day 13 Part One`() {
        val classToTest = Day13(actualData)
        assertEquals("8,9", classToTest.partOne())
    }

    @Test
    fun `Day 13 Part Two Test Example`() {
        val classToTest = Day13(testDataTwo)
        assertEquals("6,4", classToTest.partTwo())
    }

    @Test
    fun `Day 13 Part Two`() {
        val classToTest = Day13(actualData)
        assertEquals("73,33", classToTest.partTwo())
    }
}