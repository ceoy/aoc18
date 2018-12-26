package com.ceoy.aoc

import kotlin.math.absoluteValue

class Day10(input: List<String> = FileLoader.load("day10_actual.txt")) {

    val input = input.map {
        Point.parse(it)
    }

    /**
     * Creates the Picture and returns the Amount of Seconds that message would have taken to appear
     *
     * this solution assumes that the stars are compact when the message appears = small bound
     * and that all stars are used for the message
     */
    fun partOneAndTwo(imageName: String = "result"): Int {
        var oldSize = Long.MAX_VALUE
        var newSize = area(input)
        var amountOfMoves = 0

        while (oldSize >= newSize) {
            input.forEach { it.move() }
            amountOfMoves++
            oldSize = newSize
            newSize = area(input)
        }

        amountOfMoves--
        input.forEach { point -> point.moveBack() }

        val output = getImageString(input)
        ImageCreator.createImage(imageName, output)

        println("~~~~~~~~")
        println(output)

        return amountOfMoves
    }

    /**
     * Calculates the Size of the Area
     */
    private fun area(points: List<Point>): Long {
        val topY = points.minBy { it.y }!!.y.toLong()
        val leftX = points.minBy { it.x }!!.x.toLong()
        val bottomY = points.maxBy { it.y }!!.y.toLong()
        val rightX = points.maxBy { it.x }!!.x.toLong()

        return (rightX - leftX).absoluteValue * (bottomY - topY).absoluteValue
    }

    /**
     * Create the String for the Image (and/or output)
     */
    private fun getImageString(points: List<Point>): String {
        val topY = points.minBy { it.y }!!.y.toLong()
        val leftX = points.minBy { it.x }!!.x.toLong()
        val bottomY = points.maxBy { it.y }!!.y.toLong()
        val rightX = points.maxBy { it.x }!!.x.toLong()
        val width = (rightX - leftX).absoluteValue

        val outPutString = mutableListOf<String>()
        for (y in topY..bottomY) {

            // 9 - 4 => 5
            val charArray = CharArray(width.toInt() + 1) { ' ' }

            for (x in leftX..rightX) {
                points.find { it.x == x.toInt() && it.y == y.toInt() }?.let {
                    // get position in array
                    val position = x - leftX
                    charArray[position.toInt()] = '#'
                }
            }

            outPutString.add(String(charArray))
        }

        return outPutString.joinToString("\n")
    }

    data class Point(var x: Int, var y: Int, val vX: Int, val vY: Int) {
        companion object {
            /**
             * A+ Parsing, beautiful af
             */
            fun parse(input: String): Point {
                val (x, y) = input.run {
                    Pair(substring(indexOf("<") + 1 until indexOf(",")).trim().toInt(),
                            substring(indexOf(",") + 1 until indexOf(">")).trim().toInt())

                }
                val (dX, dY) = input.run {
                    Pair(substring(lastIndexOf('<') + 1 until lastIndexOf(",")).trim().toInt(),
                            substring(lastIndexOf(",") + 1 until lastIndexOf(">")).trim().toInt())
                }

                return Point(x, y, dX, dY)
            }
        }

        fun move() {
            this.x += vX
            this.y += vY
        }

        fun moveBack() {
            this.x -= vX
            this.y -= vY
        }
    }
}