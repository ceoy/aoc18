package com.ceoy.aoc

import kotlin.math.abs

class Day6(input: List<String> = FileLoader.load("day6.txt")) {

    private val locations: List<Location>
    private val left: Int
    private val right: Int
    private val top: Int
    private val bottom: Int

    init {
        locations = input.map {
            val index = it.indexOf(",")
            val x = it.substring(0, index).toInt()
            val y = it.substring(index + 2, it.length).toInt()
            Location(x, y)
        }

        left = locations.minBy { it.x }!!.x
        right = locations.maxBy { it.x }!!.x
        top = locations.minBy { it.y }!!.y
        bottom = locations.maxBy { it.y }!!.y
    }

    fun one(): Int {

        val grid = createGrid()

        grid.forEach { gridPoint ->
            val min = locations.map {
                val distance = (abs(gridPoint.first - it.x) + abs(gridPoint.second - it.y))
                it to distance
            }.sortedBy { it.second }

            if (min[0].second != min[1].second) {
                min[0].first.closeTo++
            }

            if (isInfinite(gridPoint.first, gridPoint.second)) {
                min[0].first.infinite = true
            }
        }

        return locations.filterNot { it.infinite }.maxBy {
            it.closeTo
        }?.closeTo ?: throw IllegalStateException("cannot find a max")
    }

    private fun createGrid(): MutableList<Pair<Int, Int>> {
        return mutableListOf<Pair<Int, Int>>().apply {
            mutableListOf<Pair<Int, Int>>()
            for (x in left..right) {
                for (y in top..bottom) {
                    this.add(Pair(x, y))
                }
            }
        }
    }

    private fun isInfinite(x: Int, y: Int): Boolean =
        y == top || y == bottom || x == left || x == right

    fun two(maxDistance: Int = 10000): Int {
        val grid = createGrid()

        var region = 0
        grid.forEach { gridPoint ->
            val value = locations.map {
                (abs(gridPoint.first - it.x) + abs(gridPoint.second - it.y))
            }.sum()

            if (value < maxDistance) {
                region += 1
            }
        }
        return region
    }

    data class Location(val x: Int, val y: Int, var infinite: Boolean = false, var closeTo: Int = 0)
}