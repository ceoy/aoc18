package com.ceoy.aoc.battle

data class Terrain(val position: GameObject.Position, val terrainType: GameObject.TerrainType) : GameObject {

    /**
     * Check if a GameUnit can doTurn here
     */
    fun canUnitMove(): Boolean = when (terrainType) {
        GameObject.TerrainType.WALL -> false
        GameObject.TerrainType.FLOOR -> true
    }

    companion object {
        fun parse(x: Int, y: Int, type: Char): Terrain {
            val terrainType = GameObject.TerrainType.values().find { it.parseValue == type }!!
            val position = GameObject.Position(x, y)
            return Terrain(position, terrainType)
        }
    }
}