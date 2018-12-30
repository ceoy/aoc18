package com.ceoy.aoc.battle

interface GameLogic {
    /**
     * Get the Game Terrain
     */
    fun getTerrain(): List<Terrain>

    /**
     * Get the Games current Units
     */
    fun getUnits(): List<GameUnit>

    /**
     * A Team just won, there are no more enemies !:)
     */
    fun gameWon(winningTeam: GameObject.Team)
}