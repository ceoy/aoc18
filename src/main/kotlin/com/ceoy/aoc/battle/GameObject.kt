package com.ceoy.aoc.battle

interface GameObject {

    companion object {
        fun parseGameObject(x: Int, y: Int, objectChar: Char, order: Int = -1): GameObject? {
            val position = Position(x, y)

            val isUnit = Team.values().find { it.unitChar == objectChar }
            if (isUnit != null) {
                return when (isUnit) {
                    Team.GOBLIN -> Goblin(order, position)
                    Team.ELV -> Elv(order, position)
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
        GROUND('.')
    }

    /**
     * The Different Teams
     */
    enum class Team(val unitChar: Char) {
        GOBLIN('G'),
        ELV('E')
    }

    data class Position(var x: Int, var y: Int)
}