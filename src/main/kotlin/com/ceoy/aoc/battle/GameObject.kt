package com.ceoy.aoc.battle

interface GameObject {

    companion object {
        fun parseGameObject(x: Int, y: Int, objectChar: Char, order: Int = -1): GameObject? {
            val position = Position(x, y)

            val isUnit = Team.values().find { it.unitChar == objectChar }
            if (isUnit != null) {
                return when (isUnit) {
                    // the initial order is also the unique id
                    Team.GOBLIN -> Goblin(order, position, order)
                    Team.ELV -> Elv(order, position, order)
                }
            }

            val isTerrain = TerrainType.values().find { it.parseValue == objectChar }
            if (isTerrain != null) {
                return Terrain(position, isTerrain)
            }

            // something went wrong :/
            return null
        }
    }

    enum class TerrainType(val parseValue: Char) {
        WALL('#'),
        FLOOR('.')
    }

    /**
     * The Different Teams
     */
    enum class Team(val unitChar: Char) {
        GOBLIN('G'),
        ELV('E')
    }

    data class Position(var x: Int, var y: Int) : Comparable<Position> {
        /**
         * Negative, this is smaller,
         * Zero, equal,
         * Positive, bigger
         */
        override fun compareTo(other: Position): Int {
            val yCompare = this.y - other.y
            if (yCompare != 0) {
                return yCompare
            }

            // left is lower
            return this.x - other.x
        }

        fun isPositionMovable(game: GameLogic): Boolean {
            // only take positions that are not occupied yet
            val terrainType = game.getTerrain()
                .find { it.position.y == y && it.position.x == x }
                ?.terrainType
                ?: GameObject.TerrainType.WALL

            // check if there is a unit (since that would also mean occupied)
            val unit = game.getUnits().find { it.getPosition().y == y && it.getPosition().x == x && it.isAlive() }

            return terrainType == GameObject.TerrainType.FLOOR && unit == null
        }

        fun createGraph(availablePositions: MutableList<Position>): List<Node> {
            val startNode = Node(this, null)
            val graph = mutableListOf(startNode)

            val currentIteration = mutableListOf(startNode)

            val nextIteration = mutableListOf<Node>()

            var noMoreIterations = false

            while (!noMoreIterations) {
                currentIteration.forEach { currentNode ->
                    var nodeDone = false
                    while (!nodeDone) {
                        val nextPosition = currentNode.position.findClosestPosition(availablePositions)
                        if (nextPosition == null) {
                            nodeDone = true
                        } else {
                            val node = Node(nextPosition, currentNode)
                            availablePositions.remove(nextPosition)
                            graph.add(node)

                            // also add to next iteration so we know that we also have to do this boy
                            nextIteration.add(node)
                        }
                    }
                }

                if (nextIteration.isEmpty()) {
                    noMoreIterations = true
                }

                // this iteration is done
                currentIteration.clear()
                currentIteration.addAll(nextIteration)
                nextIteration.clear()
            }

            return graph
        }

        private fun findClosestPosition(availablePositions: MutableList<Position>): Position? {
            return availablePositions
                .find { it.x == this.x && it.y == y - 1 } ?: availablePositions // top
                .find { it.x == this.x - 1 && it.y == this.y } ?: availablePositions // left
                .find { it.x == this.x + 1 && it.y == this.y } ?: availablePositions // right
                .find { it.x == this.x && it.y == this.y + 1 } // bottom
        }
    }

    data class Node(val position: Position, val parentNode: Node?)
}