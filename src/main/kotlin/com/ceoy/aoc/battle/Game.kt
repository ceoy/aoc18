package com.ceoy.aoc.battle

class Game(
    private val gameField: List<Terrain>,
    private var gameUnits: MutableList<GameUnit>,
    private val debug: Boolean
) : GameLogic {

    private var gameOver: Boolean = false
    private var turns: Int = 0
    private var score: Int = 0

    fun battle(): Int {

        if (debug) {
            println("Game is about to Start")
            visualizeGame()
        }

        while (!gameOver) {
            turn()
        }

        return score
    }

    private fun turn() {
        if (turns == 33) {
            val i = 1
        }
        // for each unit, do their turn action
        gameUnits.forEach { unit ->
            // if the game is over, there is no need to continue (it is also not allowed)
            if (gameOver) {
                return
            }

            // skip units that are not alive anymore, since zombies aren't a thing in this story
            if (!unit.isAlive()) return@forEach

            unit.doTurn(this)
        }

        // after each turn, clean up all old units, fix the order
        finishRound()
    }

    private fun finishRound() {
        gameUnits = gameUnits.filter { it.isAlive() }.sortedBy {
            it.getPosition()
        }.toMutableList()

        // update the order
        gameUnits.forEachIndexed { index, gameUnit ->
            gameUnit.updateOrder(index)
        }

        // the turn was a success :)
        turns++

        if (debug) {
            visualizeGame()
        }
    }

    private fun visualizeGame() {
        println("~~~~ Current Game State ~~~~")
        println("This is Round: $turns\n")

        var oldY = 0
        getTerrain().forEach { terrain ->

            if (terrain.position.y > oldY) {
                oldY = terrain.position.y
                println("")
            }

            // check if there is also a unit
            val unit = getUnits().find { it.getPosition() == terrain.position && it.isAlive()}
            if (unit != null) {
                print(unit.getTeam().unitChar)
            } else {
                print(terrain.terrainType.parseValue)
            }
        }

        println("\nUnits Overview:")
        getUnits().forEach { unit ->
            println(
                "Unit Nr. ${unit.getUniqueId()} from " +
                    "${unit.getTeam()} has " +
                    "${unit.getHealth()} HP left and will go next as " +
                    "${unit.getOrder() + 1}"
            )
        }

        GameObject.Team.values().forEach { team ->
            val units = getUnits().filter { it.getTeam() == team }

            println("Team $team has a total of ${units.size} Player with a combined ${units.sumBy { it.getHealth() }} HP")
        }
        println("")
    }

    override fun gameWon(winningTeam: GameObject.Team) {
        gameOver = true

        val sum = gameUnits
            .filter { it.isAlive() && it.getTeam() == winningTeam }
            .sumBy { it.getHealth() }
        score = turns * sum

        if (debug) {
            visualizeGame()
        }
    }

    companion object {
        fun parseGame(input: List<String>, debug: Boolean = false): Game {
            val units = mutableListOf<GameUnit>()
            val terrain = mutableListOf<Terrain>()

            var unitOrder = units.size
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, type ->
                    val parsedObject = GameObject.parseGameObject(x, y, type, unitOrder)

                    when (parsedObject) {
                        is GameUnit -> {
                            // add unit
                            units.add(parsedObject)
                            unitOrder++

                            // but also add a terrain, since the unit must be standing on something :)
                            terrain.add(GameObject.parseGameObject(x, y, '.') as Terrain)
                        }
                        is Terrain -> terrain.add(parsedObject)
                    }
                }
            }

            return Game(terrain, units, debug)
        }
    }

    override fun getTerrain(): List<Terrain> = gameField

    override fun getUnits(): List<GameUnit> = gameUnits
}