package com.ceoy.aoc.battle

class Goblin(private var order: Int, private val position: GameObject.Position, private val uniqueId: Int) : GameUnit {

    private var health: Int = 200
    private var attackPower: Int = 3

    override fun takeDamage(damage: Int) {
        this.health -= damage
    }

    override fun updateOrder(order: Int) {
        this.order = order
    }

    override fun getUniqueId(): Int = uniqueId

    override fun isAlive(): Boolean = this.health > 0

    override fun getOrder(): Int = order

    override fun getTeam(): GameObject.Team = GameObject.Team.GOBLIN

    override fun getPosition(): GameObject.Position = position

    override fun getHealth(): Int = health

    override fun getAttackPower(): Int = attackPower
}