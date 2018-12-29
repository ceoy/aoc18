package com.ceoy.aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day12Test {

    private val initialState =
        "###.#..#..##.##.###.#.....#.#.###.#.####....#.##..#.#.#..#....##..#.##...#.###.#.#..#..####.#.##.#"
    private val rules = FileLoader.load("day12_actual.txt")

    private val testRules = FileLoader.load("day12_example.txt")
    private val initialStateExample = "#..#.#..##......###...###"

    @Test
    fun `Day 12 Part One`() {
        val day12 = Day12(initialState, rules)
        assertEquals(2909, day12.partOne())
    }

    @Test
    fun `Day 12 Part One Example`() {
        val day12 = Day12(initialStateExample, testRules)
        assertEquals(325, day12.partOne())
    }

    @Test
    fun `Day 12 Test Generation`() {
        val generation = Day12.Generation(".#....##....#####...#######....#.#..##.", -3)
        assertEquals(325, generation.calculateValue())
    }

    @Test
    fun `Day 12 Part Two`() {
        val day12 = Day12(initialState, rules)
        assertEquals(2500000001175L, day12.partTwo())
    }
}