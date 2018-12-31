package com.ceoy.aoc

import com.ceoy.aoc.battle.Game
import com.ceoy.aoc.battle.GameObject

class Day15(
    private val gameInput: List<String>,
    private val debug: Boolean = false
) {

    fun partOne(): Int {
        val game = Game.parseGame(gameInput, debug = debug)

        return game.battle().first
    }

    fun partTwo(): Int {
        var attackPower = 4

        while (true) {
            val game = Game.parseGame(gameInput, elfAttackPower = attackPower, failOnElfDeath = true, debug = debug)
            val battleResult = game.battle()

            if (battleResult.second == GameObject.Team.ELV) {
                return battleResult.first
            } else {
                attackPower++
            }
        }
    }
}