package com.ceoy.aoc.battle

class Game(val gameField: List<Terrain>, val gameUnits: List<GameUnit>) {

    companion object {
        fun parseGame(input: List<String>): Game {
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

            return Game(terrain, units)
        }
    }
}