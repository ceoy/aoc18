package com.ceoy.aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day14Test {

    private val actualInput = 880751

    @Test
    fun `Day 14 Part One`() {
        val classToTest = Day14()
        assertEquals(880751, classToTest.partOne(actualInput))
    }

    @Test
    fun `Day 14 Part One Test Cases`() {
        val classToTest0 = Day14()
        assertEquals(5158916779, classToTest0.partOne(9))

        val classToTest1 = Day14()
        assertEquals(124515891, classToTest1.partOne(20333868))

        val classToTest2 = Day14()
        assertEquals(9251071085, classToTest2.partOne(18))

        val classToTest3 = Day14()
        assertEquals(5941429882, classToTest3.partOne(2018))
    }

    @Test
    fun `Day 14 Part Two Test Cases`() {
        val classToTest0 = Day14()
        assertEquals(9, classToTest0.partTwo("51589"))

        val classToTest1 = Day14()
        assertEquals(5, classToTest1.partTwo("01245"))

        val classToTest2 = Day14()
        assertEquals(18, classToTest2.partTwo("92510"))

        val classToTest3 = Day14()
        assertEquals(2018, classToTest3.partTwo("59414"))
    }

    @Test
    fun `Day 14 Part Two`() {
        val classToTest = Day14()
        println(classToTest.partTwo(actualInput.toString()))
    }

    @Test
    fun `Test toDigitArray`() {
        val test0 = 1235
        assertEquals(listOf(1, 2, 3, 5), test0.toDigitList())

        val test1 = 0
        assertEquals(listOf(0), test1.toDigitList())
    }

    @Test
    fun `Test toAppendedLong`() {
        val test0 = mutableListOf(1, 2, 3, 4, 5)
        assertEquals(12345L, test0.toAppendedLong())

        val test1 = listOf(0, 1, 4, 7)
        assertEquals(147, test1.toAppendedLong())

        val test2 = listOf(9, 0, 4, 7)
        assertEquals(9047, test2.toAppendedLong())

        val test3 = listOf(5, 1, 5, 8, 9, 1, 6, 7, 7, 9)
        assertEquals(5158916779L, test3.toAppendedLong())
    }
}