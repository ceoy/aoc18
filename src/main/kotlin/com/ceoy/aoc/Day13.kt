package com.ceoy.aoc

class Day13(private val input: List<String>) {

    fun partOne(): String {

        val (grid, carts) = parseGrid()

        while (true) {
            grid.forEachIndexed y@{ y, width ->
                width.forEachIndexed x@{ x, lineType ->
                    if (lineType == LineType.NONE) return@x // // continue with the loop

                    val cart = carts.find { it.x == x && it.y == y && !it.cartMoved }

                    cart?.let {
                        // move cart
                        cart.tick(grid)

                        val cartsAtPosition = carts.filter { filterCart ->
                            filterCart.x == cart.x && filterCart.y == cart.y
                        }

                        if (cartsAtPosition.size > 1) {
                            return cartsAtPosition.first().cartString()
                        }
                    }
                }
            }

            // update cart logic
            carts.forEach {
                it.cartMoved = false
            }
        }
    }

    fun partTwo(): String {
        val (grid, carts) = parseGrid()

        while (true) {
            grid.forEachIndexed y@{ y, width ->
                width.forEachIndexed x@{ x, lineType ->
                    if (lineType == LineType.NONE) return@x // // continue with the loop

                    val cart = carts.find { it.x == x && it.y == y && !it.cartMoved }

                    cart?.let {
                        // move cart
                        cart.tick(grid)

                        val cartsAtPosition = carts.filter { filterCart ->
                            filterCart.x == cart.x && filterCart.y == cart.y
                        }

                        if (cartsAtPosition.size > 1) {
                            carts.removeAll(cartsAtPosition)
                        }
                    }
                }
            }

            // update cart logic
            carts.forEach {
                it.cartMoved = false
            }

            if (carts.size == 1) {
                return carts.first().cartString()
            }
        }
    }

    private fun parseGrid(): Pair<ArrayList<ArrayList<LineType>>, MutableList<Cart>> {

        val grid = arrayListOf<ArrayList<LineType>>()
        val carts = mutableListOf<Cart>()
        val minWidth = input.maxBy {
            it.length
        }!!.length

        input.forEachIndexed { y, input ->
            grid.add(arrayListOf())

            input.forEachIndexed { x, it ->
                grid[y].add(when (it) {
                    // carts
                    '<' -> {
                        carts.add(Cart(x, y, Direction.LEFT))
                        LineType.PATH_HORIZONTAL
                    }
                    '>' -> {
                        carts.add(Cart(x, y, Direction.RIGHT))
                        LineType.PATH_HORIZONTAL
                    }
                    '^' -> {
                        carts.add(Cart(x, y, Direction.UP))
                        LineType.PATH_VERTICAL
                    }
                    'v' -> {
                        carts.add(Cart(x, y, Direction.DOWN))
                        LineType.PATH_VERTICAL
                    }

                    // intersection
                    '+' -> LineType.INTERSECTION

                    // paths
                    '-' -> LineType.PATH_HORIZONTAL
                    '|' -> LineType.PATH_VERTICAL

                    // curves
                    '/' -> LineType.R_CURVE
                    '\\' -> LineType.L_CURVE

                    else -> LineType.NONE
                })
            }

            // fill grid :)
            while (grid[y].size < minWidth) {
                grid[y].add(LineType.NONE)
            }
        }

        return Pair(grid, carts)
    }

    /**
     * Assumptions about carts and position:
     * 1. Initial Position is not on a intersection
     */
    data class Cart(var x: Int,
                    var y: Int,
                    var directionFacing: Direction,
                    var memory: Int = 0,
                    var cartMoved: Boolean = false) {

        fun cartString(): String {
            return "$x,$y"
        }

        fun tick(grid: ArrayList<ArrayList<LineType>>) {
            // move
            move()

            // adjust where to go next
            updateDirection(grid)

            // do not move multiple times a tick
            this.cartMoved = true
        }

        /**
         * 💩💩💩💩💩💩💩
         */
        private fun updateDirection(grid: ArrayList<ArrayList<LineType>>) {
            val lineType = grid[y][x]
            directionFacing = when (lineType) {

                LineType.PATH_HORIZONTAL,
                LineType.PATH_VERTICAL -> directionFacing // we are already facing this direction, we could'nt have come here otherwise
                LineType.R_CURVE -> {
                    when (directionFacing) {
                        Direction.UP -> Direction.RIGHT
                        Direction.DOWN -> Direction.LEFT
                        Direction.RIGHT -> Direction.UP
                        Direction.LEFT -> Direction.DOWN
                    }
                }
                LineType.L_CURVE -> {
                    when (directionFacing) {
                        Direction.UP -> Direction.LEFT
                        Direction.DOWN -> Direction.RIGHT
                        Direction.RIGHT -> Direction.DOWN
                        Direction.LEFT -> Direction.UP
                    }
                }
                LineType.INTERSECTION -> {
                    val nextDirection = when (memory % 3) {
                        0 -> directionFacing.rotateLeft()
                        2 -> directionFacing.rotateRight()
                        else -> directionFacing
                    }
                    memory++
                    nextDirection
                }
                LineType.NONE -> throw IllegalStateException("Cart cannot move to Line Type None}")
            }
        }

        private fun move() {
            when (directionFacing) {
                Direction.UP -> this.y--
                Direction.DOWN -> this.y++
                Direction.RIGHT -> this.x++
                Direction.LEFT -> this.x--
            }
        }
    }

    enum class LineType {
        PATH_HORIZONTAL,
        PATH_VERTICAL,
        R_CURVE,
        L_CURVE,
        INTERSECTION,
        NONE;

        override fun toString(): String {
            return when (this) {
                PATH_HORIZONTAL -> "-"
                PATH_VERTICAL -> "|"
                R_CURVE -> "/"
                L_CURVE -> "\\"
                INTERSECTION -> "+"
                NONE -> " "
            }
        }
    }

    enum class Direction {
        UP, DOWN, RIGHT, LEFT;

        fun rotateLeft(): Direction {
            return when (this) {
                UP -> LEFT
                DOWN -> RIGHT
                RIGHT -> UP
                LEFT -> DOWN
            }
        }

        fun rotateRight(): Direction {
            return when (this) {
                UP -> RIGHT
                DOWN -> LEFT
                RIGHT -> DOWN
                LEFT -> UP
            }
        }

        override fun toString(): String {
            return when (this) {
                UP -> "^"
                DOWN -> "v"
                RIGHT -> ">"
                LEFT -> "<"
            }
        }
    }

    /**
     * Debug Map
     */
    private fun printGrid(grid: ArrayList<ArrayList<LineType>>, carts: List<Cart>) {
        grid.forEachIndexed { y, line ->
            line.forEachIndexed { x, lineType ->
                val outPut = carts.find {
                    it.x == x && it.y == y
                }?.directionFacing?.toString() ?: lineType.toString()

                print(outPut)
            }
            println("")
        }
    }
}