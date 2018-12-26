package com.ceoy.aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day9Test {

    private val zero = listOf("9 players: last marble is worth 25 points")
    private val one = listOf("10 players; last marble is worth 1618 points")
    private val two = listOf("13 players; last marble is worth 7999 points")
    private val three = listOf("17 players; last marble is worth 1104 points")
    private val four = listOf("21 players; last marble is worth 6111 points")
    private var five = listOf("30 players; last marble is worth 5807 points")

    @Test
    fun partOneExample() {
        val zero = Day9(zero)
        assertEquals(32, zero.partOne())

        val one = Day9(one)
        assertEquals(8317, one.partOne())

        val two = Day9(two)
        assertEquals(146373, two.partOne())

        val three = Day9(three)
        assertEquals(2764, three.partOne())

        val four = Day9(four)
        assertEquals(54718, four.partOne())

        val five = Day9(five)
        assertEquals(37305, five.partOne())

    }

    @Test
    fun partOneActual() {
        val actualClass = Day9()
        assertEquals(390093L, actualClass.partOne())
    }

    @Test
    fun partTwoActual() {
        val actualClass = Day9()
        assertEquals(3150377341L, actualClass.partTwo())
    }
}