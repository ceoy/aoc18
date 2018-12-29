package com.ceoy.aoc

import com.ceoy.aoc.battle.Game
import com.ceoy.aoc.battle.GameObject
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Day15Test {

    private val gameCreationUnitOrder = FileLoader.load("day15_game_test.txt")

    @Test
    fun `Game Test Unit Order and Game Creation`() {
        val game = Game.parseGame(gameCreationUnitOrder)

        assertEquals(7, game.gameUnits.size)
        assertEquals(35, game.gameField.size)

        // check first and last row
        (0..6).forEach { x ->
            val terrain = game.gameField.find { terrain ->
                terrain.position.x == x && terrain.position.y == 0
            }

            assertNotNull(terrain)
            assertEquals(GameObject.TerrainType.WALL, terrain.terrainType)

            val terrainBottom = game.gameField.find { terrainBottom ->
                terrainBottom.position.x == x && terrainBottom.position.y == 4
            }

            assertNotNull(terrainBottom)
            assertEquals(GameObject.TerrainType.WALL, terrainBottom.terrainType)
        }

        // check ground (middle blocks)
        (1..5).forEach { x ->
            (1..3).forEach { y ->
                val terrain = game.gameField.find { terrain ->
                    terrain.position.x == x && terrain.position.y == y
                }

                assertNotNull(terrain)
                assertEquals(GameObject.TerrainType.GROUND, terrain.terrainType)
            }
        }

        // check the walls
        (0..4).forEach { y ->
            val terrainStart = game.gameField.find { terrain ->
                terrain.position.x == 0 && terrain.position.y == y
            }
            val terrainEnd = game.gameField.find { terrain ->
                terrain.position.x == 6 && terrain.position.y == y
            }

            assertNotNull(terrainEnd)
            assertNotNull(terrainStart)

            assertEquals(GameObject.TerrainType.WALL, terrainEnd.terrainType)
            assertEquals(GameObject.TerrainType.WALL, terrainStart.terrainType)
        }

        // check game Units
        game.gameUnits.forEachIndexed { index, gameUnit ->
            assertEquals(index, gameUnit.getOrder())

            if (index == 0 || index == 3 || index == 5) {
                assertEquals(GameObject.Team.GOBLIN, gameUnit.getTeam())
            } else {
                assertEquals(GameObject.Team.ELV, gameUnit.getTeam())
            }
        }

        val firstUnit = game.gameUnits.find { it.getPosition() == GameObject.Position(2, 1) }
        assertNotNull(firstUnit)

        val fiftUnit = game.gameUnits.find { it.getPosition() == GameObject.Position(5, 2) }
        assertNotNull(firstUnit)
    }
}



