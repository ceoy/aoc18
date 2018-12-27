package com.ceoy.aoc

class Day11(private val serialNumber: Int) {


    fun partOne(): String {
        val grid = createGrid()

        var maxPower = Int.MIN_VALUE
        var maxX = 0
        var maxY = 0

        for (x in 0 until 300) {
            for (y in 0 until 300) {
                val power = getSquarePowerLevel(x, y, grid)
                if (power > maxPower) {
                    maxPower = power
                    maxX = x
                    maxY = y

                }
            }
        }

        return "${maxX + 1},${maxY + 1}"
    }

    private fun createGrid(): ArrayList<IntArray> {
        // create grid
        val grid = arrayListOf<IntArray>()
        // fill that array
        for (x in 0 until 300) {
            grid.add(IntArray(300) {
                getPowerLevel(x + 1, it + 1, serialNumber)
            })
        }

        return grid
    }

    fun partTwo(): String {

        val grid = createGrid()

        var maxPower = Int.MIN_VALUE
        var maxX = 0
        var maxY = 0
        var squareSize = 0

        for (x in 0 until 300) {
            for (y in 0 until 300) {

                val squareX = 300 - x
                val squareY = 300 - y

                val biggest = if (squareX - squareY > 0) squareY else squareX

                (1..biggest).asSequence().forEach {
                    val power = getSquarePowerLevel(x, y, grid, it)
                    if (power > maxPower) {
                        maxPower = power
                        maxX = x
                        maxY = y
                        squareSize = it
                    }
                }
            }
        }

        return "${maxX + 1},${maxY + 1},$squareSize"
    }

    private fun getSquarePowerLevel(xPos: Int, yPos: Int,
                                    grid: ArrayList<IntArray>,
                                    squareSize: Int = 3): Int {

        // do not calculate if the square is too big
        if (300 - xPos < squareSize && 300 - yPos < squareSize) {
            return Int.MIN_VALUE
        }

        var power = 0

        (xPos until xPos + squareSize).forEach { x ->
            (yPos until yPos + squareSize).forEach { y ->
                if (grid.size > x && grid[x].size > y) {
                    power += grid[x][y]
                }
            }
        }

        return power
    }

    companion object {
        fun getPowerLevel(x: Int, y: Int, serialNumber: Int): Int {
            val rackId = x + 10
            var powerLevel = rackId * y
            powerLevel += serialNumber
            powerLevel *= rackId
            powerLevel = powerLevel / 100 % 10

            return powerLevel - 5
        }
    }
}