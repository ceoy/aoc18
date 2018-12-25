package com.ceoy.aoc

class Day3 {

    private val input = FileLoader.load("day3.txt").map { Claim.parse(it) }

    fun one() {
        val grid = mutableMapOf<Int, MutableMap<Int, Int>>()

        doLoop(grid) { key, depth, _ ->
            depth[key] = depth.getOrPut(key) { 0 } + 1
        }

        var counter = 0
        grid.forEach { _, value ->
            value.forEach {
                if (it.value > 1) {
                    counter += 1
                }
            }
        }

        println(counter)
    }

    fun two() {
        val grid = mutableMapOf<Int, MutableMap<Int, Int>>()
        val overlappingClaims = mutableListOf<Claim>().apply { addAll(input) }
        doLoop(grid) { key, depth, claim ->
            if (depth.getOrPut(key) { -1 } == -1) {
                depth[key] = claim.id
            } else {
                overlappingClaims.removeAll { overlappingClaim ->
                    overlappingClaim.id == claim.id || overlappingClaim.id == depth[key]
                }
            }
        }

        if (overlappingClaims.size != 1) {
            throw IllegalStateException("This is a Fail :(")
        }

        println(overlappingClaims[0].id)
    }

    private fun doLoop(grid: MutableMap<Int, MutableMap<Int, Int>>, block: (key: Int, depth: MutableMap<Int, Int>, claim: Claim) -> Unit) {
        // calculate the position
        input.forEach { claim ->
            // get position in first list
            val widthIndex = claim.marginLeft
            val heightIndex = claim.marginTop

            for (i in 0 until claim.width) {
                for (y in 0 until claim.height) {
                    // get position
                    val key = heightIndex + y
                    val actualI = widthIndex + i

                    if (grid[actualI].isNullOrEmpty())
                        grid[actualI] = mutableMapOf()

                    grid[actualI]?.let {
                        block(key, it, claim)
                    }
                }
            }
        }

    }

    data class Claim(val id: Int, val marginLeft: Int, val marginTop: Int, val width: Int, val height: Int) {
        companion object {
            fun parse(line: String): Claim {
                val id = line.substring(1, line.indexOf('@')).trim().toInt()

                val marginLeft = line.substring(line.indexOf('@') + 1, line.indexOf(',')).trim().toInt()
                val marginTop = line.substring(line.indexOf(',') + 1, line.indexOf(':')).trim().toInt()
                val width = line.substring(line.indexOf(':') + 1, line.indexOf('x')).trim().toInt()
                val heightStr = line.substring(line.indexOf('x') + 1, line.length).trim()
                val height = heightStr.toInt()
                return Claim(id, marginLeft, marginTop, width, height)
            }
        }

    }
}