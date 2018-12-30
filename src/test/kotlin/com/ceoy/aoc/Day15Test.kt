package com.ceoy.aoc

import com.ceoy.aoc.battle.Elv
import com.ceoy.aoc.battle.Game
import com.ceoy.aoc.battle.GameObject
import com.ceoy.aoc.battle.Goblin
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Day15Test {

    private val gameCreationUnitOrder = FileLoader.load("day15_game_test.txt")
    private val actualGame = FileLoader.load("day15_actual.txt")
    private val testGame = FileLoader.load("day15_test_game.txt")
    private val testGameTwo = FileLoader.load("day15_test_game2.txt")
    private val testGameThee = FileLoader.load("day15_test_game3.txt")
    private val testGameFour = FileLoader.load("day15_test_game4.txt")
    private val testGameFive = FileLoader.load("day15_test_game5.txt")
    private val testGameSix = FileLoader.load("day15_test_game6.txt")
    private val testPathing = FileLoader.load("day15_pathing.txt")

    @Test
    fun `Day 15 Test Pathing`() {
        val game = Game.parseGame(testPathing)
        val elv = game.getUnits()[2]
        elv.doTurn(game)

        assertEquals(GameObject.Position(4, 2), elv.getPosition())
    }

    @Test
    fun `Day 15 Part One Test Game`() {
        val game = Game.parseGame(testGame, true)
        assertEquals(18740, game.battle())
    }

    @Test
    fun `Day 15 Part One Test Game Two`() {
        val game = Game.parseGame(testGameTwo)
        assertEquals(28944, game.battle())
    }

    @Test
    fun `Day 15 Part One Test Game Three`() {
        val game = Game.parseGame(testGameThee)
        assertEquals(27755, game.battle())
    }

    @Test
    fun `Day 15 Part One Test Game Four`() {
        val game = Game.parseGame(testGameFour, true)
        assertEquals(39514, game.battle())
    }

    @Test
    fun `Day 15 Part One Test Game Five`() {
        val game = Game.parseGame(testGameFive)
        assertEquals(36334, game.battle())
    }

    @Test
    fun `Day 15 Part One Test Game Six`() {
        val game = Game.parseGame(testGameSix, true)
        assertEquals(27730, game.battle())
    }

    @Test
    fun `Test Actual Game`() {
        val game = Game.parseGame(actualGame, true)
        println(game.battle())
    }

    @Test
    fun `Game Test Unit Order and Game Creation`() {
        val game = Game.parseGame(gameCreationUnitOrder)

        assertEquals(7, game.getUnits().size)
        assertEquals(35, game.getTerrain().size)

        // check first and last row
        (0..6).forEach { x ->
            val terrain = game.getTerrain().find { terrain ->
                terrain.position.x == x && terrain.position.y == 0
            }

            assertNotNull(terrain)
            assertEquals(GameObject.TerrainType.WALL, terrain.terrainType)

            val terrainBottom = game.getTerrain().find { terrainBottom ->
                terrainBottom.position.x == x && terrainBottom.position.y == 4
            }

            assertNotNull(terrainBottom)
            assertEquals(GameObject.TerrainType.WALL, terrainBottom.terrainType)
        }

        // check ground (middle blocks)
        (1..5).forEach { x ->
            (1..3).forEach { y ->
                val terrain = game.getTerrain().find { terrain ->
                    terrain.position.x == x && terrain.position.y == y
                }

                assertNotNull(terrain)
                assertEquals(GameObject.TerrainType.FLOOR, terrain.terrainType)
            }
        }

        // check the walls
        (0..4).forEach { y ->
            val terrainStart = game.getTerrain().find { terrain ->
                terrain.position.x == 0 && terrain.position.y == y
            }
            val terrainEnd = game.getTerrain().find { terrain ->
                terrain.position.x == 6 && terrain.position.y == y
            }

            assertNotNull(terrainEnd)
            assertNotNull(terrainStart)

            assertEquals(GameObject.TerrainType.WALL, terrainEnd.terrainType)
            assertEquals(GameObject.TerrainType.WALL, terrainStart.terrainType)
        }

        // check game Units
        game.getUnits().forEachIndexed { index, gameUnit ->
            assertEquals(index, gameUnit.getOrder())

            if (index == 0 || index == 3 || index == 5) {
                assertEquals(GameObject.Team.GOBLIN, gameUnit.getTeam())
            } else {
                assertEquals(GameObject.Team.ELV, gameUnit.getTeam())
            }
        }

        val firstUnit = game.getUnits().find { it.getPosition() == GameObject.Position(2, 1) }
        assertNotNull(firstUnit)

        val fifthUnit = game.getUnits().find { it.getPosition() == GameObject.Position(5, 2) }
        assertNotNull(fifthUnit)
    }

    @Test
    fun `Test sorting Order of Units`() {
        // why am i scared that this is gonna fail?? :<
        val units = listOf(
            Elv(0, GameObject.Position(0, 2), 0),
            Goblin(1, GameObject.Position(2, 0), 0)
        )

        val sortedUnits = units.sortedBy { it.getPosition() }
        assertEquals(1, sortedUnits[0].getOrder())
        assertEquals(0, sortedUnits[1].getOrder())
    }
}



