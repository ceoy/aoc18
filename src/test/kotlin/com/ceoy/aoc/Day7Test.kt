package com.ceoy.aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day7Test {

    private val exampleStringList = listOf(
        "Step C must be finished before step A can begin.",
        "Step C must be finished before step F can begin.",
        "Step A must be finished before step B can begin.",
        "Step A must be finished before step D can begin.",
        "Step B must be finished before step E can begin.",
        "Step D must be finished before step E can begin.",
        "Step F must be finished before step E can begin."
    )

    @Test
    fun `Day 7 Part One Example`() {
        val dayClass = Day7()
        assertEquals("CABDFE", dayClass.one(exampleStringList))
    }

    @Test
    fun `Day 7 Part One Actual`() {
        val dayClass = Day7()
        assertEquals("EPWCFXKISTZVJHDGNABLQYMORU", dayClass.one())
    }

    @Test
    fun `Day 7 Part Two Example`() {
        val dayClass = Day7()
        assertEquals(15, dayClass.two(exampleStringList, 0, 2))
    }

    @Test
    fun `Day 7 Part Two Actual`() {
        val dayClass = Day7()
        assertEquals(952, dayClass.two())
    }
}