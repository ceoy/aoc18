package com.ceoy.aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day8Test {

    private val exampleInput = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"

    @Test
    fun day8PartOneExample() {
        val classToTest = Day8(listOf(exampleInput))
        assertEquals(138, classToTest.partOne())
    }

    @Test
    fun day8PartOneActual() {
        val classToTest = Day8()
        println(classToTest.partOne())
    }

    @Test
    fun day8PartTwoExample() {
        val classToTest = Day8(listOf(exampleInput))
        assertEquals(66, classToTest.partTwo())
    }

    @Test
    fun day8PartTwoActual() {
        val classToTest = Day8()
        println(classToTest.partTwo())
    }
}