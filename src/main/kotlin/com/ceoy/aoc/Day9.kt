package com.ceoy.aoc

import java.util.ArrayDeque

class Day9(input: List<String> = FileLoader.load("day9.txt")) {

    private val game = input.last().split(" ").run {
        Game(this[0].toInt(), this[6].toInt())
    }

    fun partOne(): Long {
        return playTheMarbles(game)
    }

    private fun playTheMarbles(game: Game): Long {
        val (playerAmount, points) = game
        // create map for the elves that want to play
        val playerList = LongArray(playerAmount) { 0L }

        // add first marble to list already
        // first is always current marble
        val circle = ArrayDeque<Int>().apply {
            this.add(0)
        }

        // since we started with adding 0 already, loop starts with 1
        for (i in 1..points) {
            // check if i is a multiply of 23
            if (i % 23 == 0) {
                val playerPosition = i % playerAmount

                playerList[playerPosition] += i.toLong()
                for (a in 1..7) {
                    circle.addFirst(circle.removeLast())
                }
                playerList[playerPosition] += circle.removeLast().toLong()
                circle.addLast(circle.removeFirst())
            } else {
                // doTurn one
                circle.addLast(circle.removeFirst())
                circle.addLast(i)
            }
        }

        return playerList.max()!!
    }

    fun partTwo(): Long {
        game.points *= 100
        return playTheMarbles(game)
    }

    private data class Game(val players: Int, var points: Int)
}
