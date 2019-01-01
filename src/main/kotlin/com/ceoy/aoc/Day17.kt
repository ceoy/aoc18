package com.ceoy.aoc

class Day17(private val input: List<String>) {

    fun partOne(): Int {

        val grid = createAreaToFill(input)

        visualizeGrid(grid)

        return 0
    }

    private fun visualizeGrid(grid: List<List<Type>>) {
        grid.forEach {
            it.forEach { type ->
                print(type.renderChar)
            }
            println("")
        }
    }

    private fun createAreaToFill(input: List<String>): List<List<Type>> {
        // parse lines first
        val allClay = input.flatMap {
            Clay.parse(it)
        }

        val maxY = allClay.maxBy { it.y }!!.y
        val maxX = allClay.maxBy { it.x }!!.x + 1 // add one, because water could flow down next to it

        val grid = mutableListOf<MutableList<Type>>()
        (0..maxY).forEach { y ->
            grid.add(mutableListOf())
            (0..maxX).forEach { x ->
                if (allClay.find { it.x == x && it.y == y } != null) {
                    grid[y].add(Type.CLAY)
                } else {
                    grid[y].add(Type.SAND)
                }
            }
        }

        grid[0][500] = Type.RESERVOIR

        return grid
    }

    data class Clay(val x: Int, val y: Int) {
        companion object {
            fun parse(line: String): List<Clay> {
                val allTheClay = mutableListOf<Clay>()

                val values = line
                    .replace("x=", "")
                    .replace(",", "")
                    .replace("y=", "")
                    .replace("..", " ")
                    .split(" ")
                    .map(String::toInt)
                    .toIntArray()

                if (line[0] == 'x') {
                    (values[1]..values[2]).map {
                        allTheClay.add(Clay(values[0], it))
                    }
                } else {
                    (values[1]..values[2]).map {
                        allTheClay.add(Clay(it, values[0]))
                    }
                }

                return allTheClay
            }
        }
    }

    enum class Type(val renderChar: Char) {
        SAND('.'),
        CLAY('#'),
        RESTING_WATER('~'),
        FALLING_WATER('|'),
        RESERVOIR('+')
    }
}