package com.ceoy.aoc.battle

interface GameUnit : GameObject {
    /**
     * get the Order bli bla blub
     */
    fun getOrder(): Int

    /**
     * Move the GameUnit
     */
    fun move()

    /**
     *
     */
    fun attack()

    /**
     * Get the Team of the GameUnit
     */
    fun getTeam(): GameObject.Team

    /**
     * Kills an GameUnit
     */
    fun die()

    /**
     * Get the Health of the GameUnit
     */
    fun getHealth(): Int

    /**
     * Get the Attack Power of the GameUnit
     */
    fun getAttackPower(): Int

    /**
     * Get the Position of the GameUnit
     */
    fun getPosition(): GameObject.Position
}