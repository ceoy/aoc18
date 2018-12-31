package com.ceoy.aoc.battle

interface GameUnit : GameObject {
    /**
     * Move the GameUnit
     */
    fun doTurn(game: GameLogic) {
        // check if we can attack
        val enemies = getEnemies(game)
        if (enemies.isEmpty()) {
            game.gameWon(this.getTeam())
            return
        }

        // check if in range of enemy
        var enemyToAttack = getAdjacentEnemy(enemies)

        if (enemyToAttack != null) {
            this.attack(enemyToAttack, game)
        } else {
            // move, if possible
            val nextPositionToMoveTo = findMoveToMake(enemies, game)
            if (nextPositionToMoveTo != null) {
                this.getPosition().apply {
                    x = nextPositionToMoveTo.x
                    y = nextPositionToMoveTo.y
                }

                // check if we can attack again
                // reevaluate enemies, since some could have died by now
                enemyToAttack = getAdjacentEnemy(getEnemies(game))
                if (enemyToAttack != null) {
                    this.attack(enemyToAttack, game)
                }
            }
        }
    }

    private fun getEnemies(game: GameLogic): List<GameUnit> =
        game.getUnits().filter { it.getTeam() != this.getTeam() && it.isAlive() }

    private fun findMoveToMake(enemies: List<GameUnit>, game: GameLogic): GameObject.Position? {
        // rules: can only move up, down, left, right
        // cannot move through wall
        // cannot move through game units

        // check in range (get positions around the objects), we will move to one of the closest positions
        val pointsToGoTo = enemies.flatMap {
            val top = it.getPosition().run { copy(y = y - 1) }
            val left = it.getPosition().run { copy(x = x - 1) }
            val bottom = it.getPosition().run { copy(y = y + 1) }
            val right = it.getPosition().run { copy(x = x + 1) }
            listOf(top, left, bottom, right)
        }.filter { newPosition ->
            newPosition.isPositionMovable(game)
        }.distinct()

        // check if objects are reachable
        // get unique points that are possible to move at
        val movablePoints = game.getTerrain().map { it.position }.filter { it.isPositionMovable(game) }.toMutableList()

        // create graph with current position to all possible positions
        val graph = getPosition().createGraph(movablePoints)

        // if only the current location is possible, then do not move at all
        if (graph.size == 1) {
            return null
        }

        // step three: check nearest
        val shortestPaths = pointsToGoTo.mapNotNull { point ->
            val initialNode = graph.find {
                it.position == point
            }

            if (initialNode == null) {
                null
            } else {
                var stepCounter = 1 // we assume one step is always possible
                var checkNode = initialNode
                while (checkNode!!.parentNode != null) {
                    checkNode = checkNode.parentNode
                    stepCounter++
                }

                Pair(initialNode, stepCounter)
            }
        }.sortedBy {
            it.second
        }

        if (shortestPaths.isEmpty()) {
            return null
        }

        val shortestPosition = shortestPaths.first()
        val nextPosition = shortestPaths.filter {
            it.second == shortestPosition.second
        }.sortedBy {
            // this makes sure that we walk to the correct position first
            it.first.position
        }.first()

        // step four: chose nearest (or reading order)
        val positionNode = nextPosition.first // this is the position we want to go to
        // find the first step in this node
        var lastNode = positionNode
        while (true) {
            if (lastNode.parentNode != null) {
                val nodeThatMightBeLast = lastNode.parentNode!!
                if (nodeThatMightBeLast.parentNode != null) {
                    lastNode = nodeThatMightBeLast
                } else {
                    return lastNode.position
                }
            }
        }
    }

    private fun getAdjacentEnemy(enemies: List<GameUnit>): GameUnit? {
        val currentPosition = this.getPosition()
        val enemiesInRange = enemies.filter {
            // check for enemies around the current player
            ((it.getPosition().x == currentPosition.x && it.getPosition().y == currentPosition.y + 1) ||
                (it.getPosition().x == currentPosition.x && it.getPosition().y == currentPosition.y - 1) ||
                (it.getPosition().x == currentPosition.x + 1 && it.getPosition().y == currentPosition.y) ||
                (it.getPosition().x == currentPosition.x - 1 && it.getPosition().y == currentPosition.y))
        }.sortedBy { it.getHealth() } // sort by health (lowest first)

        if (enemiesInRange.isEmpty()) return null

        return enemiesInRange.filter {
            // check if there are multiple enemies with the same amount of health
            enemiesInRange[0].getHealth() == it.getHealth()
        }.sortedBy { it.getPosition() }.firstOrNull()
    }

    /**
     * Get the Order, which is also the Id
     */
    fun getOrder(): Int

    /**
     * Get the Unique Id of the Game Unit
     */
    fun getUniqueId(): Int

    /**
     * Attacks a Player
     */
    fun attack(target: GameUnit, gameLogic: GameLogic) {
        target.takeDamage(this.getAttackPower())

        if (!target.isAlive()) {
            gameLogic.onUnitDeath(target.getTeam())
        }
    }

    /**
     * This Unit takes [damage] Damage
     */
    fun takeDamage(damage: Int)

    /**
     * Get the Team of the GameUnit
     */
    fun getTeam(): GameObject.Team

    /**
     * Check if the Unit is alive. The unit will be removed from the game after the round
     */
    fun isAlive(): Boolean

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

    /**
     * Updates the Order of the Unit
     */
    fun updateOrder(order: Int)
}