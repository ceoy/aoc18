package com.ceoy.aoc

import com.ceoy.aoc.battle.Game

class Day15(private val gameInput: List<String>) {

    fun partONe(): Int {
        val game = Game.parseGame(gameInput)

        return game.battle()
    }
}