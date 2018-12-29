package com.ceoy.aoc

import java.lang.StringBuilder

class Day14 {

    fun partOne(recipeToImprove: Int): Long {

        // quality score is % 10
        val scoreBoard = mutableListOf(3, 7)

        val hans = Elv("Hans the Elv", 0)
        val elmo = Elv("ELmo the Elv", 1)

        repeat(recipeToImprove + 10) {
            val (hansScore, elmoScore) = Pair(scoreBoard[hans.position], scoreBoard[elmo.position])
            val newRecipe = hansScore + elmoScore

            val recipesToAdd = newRecipe.toDigitList()
            scoreBoard.addAll(recipesToAdd)

            hans.position = (hans.position + hansScore + 1) % scoreBoard.size
            elmo.position = (elmo.position + elmoScore + 1) % scoreBoard.size
        }

        return scoreBoard.takeAt(10, recipeToImprove).toLong()
    }

    fun  partTwo(wantedResult: String): Int {
        // quality score is % 10
        val hans = Elv("Hans the Elv", 0)
        val elmo = Elv("ELmo the Elv", 1)

        val scoreBoard = StringBuilder("37")

        while(scoreBoard.takeLast(wantedResult.length) != wantedResult) {
            val (hansScore, elmoScore) = Pair(scoreBoard[hans.position].toNumber(), scoreBoard[elmo.position].toNumber())
            val newRecipe = hansScore + elmoScore

            scoreBoard.append(newRecipe.toString())

            hans.position = (hans.position + hansScore + 1) % scoreBoard.length
            elmo.position = (elmo.position + elmoScore + 1) % scoreBoard.length
        }

        return scoreBoard.indexOf(wantedResult)
    }

    data class Elv(val name: String, var position: Int)
}