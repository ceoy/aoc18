package com.ceoy.aoc.battle

class Game(
    private val gameField: List<Terrain>,
    private var gameUnits: MutableList<GameUnit>,
    private val failOnElfDeath: Boolean,
    private val debug: Boolean
) : GameLogic {

    private var gameOver: Boolean = false
    private var turns: Int = 0
    private var score: Int = 0

    private val width = gameField.maxBy { it.position.x }!!.position.x - gameField.minBy { it.position.x }!!.position.x
    private val height = gameField.maxBy { it.position.y }!!.position.y - gameField.minBy { it.position.y }!!.position.y

    fun battle(): Pair<Int, GameObject.Team> {

        if (debug) {
            println("Game is about to Start")
            visualizeGame()
        }

        while (!gameOver) {
            turn()
        }

        return Pair(score, gameUnits.first().getTeam())
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

        (0 until height).forEach y@{ y ->
            val unitsThisHeight = mutableListOf<GameUnit>()
            (0 until width).forEach x@{ x ->
                val terrain = getTerrain().find { it.position == GameObject.Position(x, y) } ?: return@x

                // check if there is also a unit
                val unit = getUnits().find { it.getPosition() == terrain.position && it.isAlive() }
                if (unit != null) {
                    unitsThisHeight.add(unit)
                    print(unit.getTeam().unitChar)
                } else {
                    print(terrain.terrainType.parseValue)
                }
            }

            // now also print units
            unitsThisHeight.forEach { unit ->
                print(" ${unit.getTeam().unitChar}(${unit.getHealth()}),")
            }

            println("")
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
        fun parseGame(
            input: List<String>,
            elfAttackPower: Int = 3,
            failOnElfDeath: Boolean = false,
            debug: Boolean = false
        ): Game {
            val units = mutableListOf<GameUnit>()
            val terrain = mutableListOf<Terrain>()

            var unitOrder = units.size
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, type ->
                    val parsedObject = GameObject.parseGameObject(x, y, type, unitOrder)

                    when (parsedObject) {
                        is GameUnit -> {
                            // add unit
                            if (parsedObject is Elv) {
                                // increase elv attack power, for part two
                                parsedObject.setAttackPower(elfAttackPower)
                            }

                            units.add(parsedObject)
                            unitOrder++

                            // but also add a terrain, since the unit must be standing on something :)
                            terrain.add(GameObject.parseGameObject(x, y, '.') as Terrain)
                        }
                        is Terrain -> terrain.add(parsedObject)
                    }
                }
            }

            return Game(terrain, units, failOnElfDeath = failOnElfDeath, debug = debug)
        }
    }

    override fun onUnitDeath(team: GameObject.Team) {
        if (failOnElfDeath && team == GameObject.Team.ELV) {
            gameOver = true

            // the gobos won :(
            gameWon(GameObject.Team.GOBLIN)
        }
    }

    override fun getTerrain(): List<Terrain> = gameField

    override fun getUnits(): List<GameUnit> = gameUnits
}