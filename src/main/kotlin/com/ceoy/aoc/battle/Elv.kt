package com.ceoy.aoc.battle

class Elv(private val order: Int, private val position: GameObject.Position) : GameUnit {

    private var health: Int = 0
    private var attackPower: Int = 0

    override fun move() {
    }

    override fun attack() {
    }

    override fun die() {
    }

    override fun getOrder(): Int = order

    override fun getTeam(): GameObject.Team = GameObject.Team.ELV

    override fun getHealth(): Int = health

    override fun getAttackPower(): Int = attackPower

    override fun getPosition(): GameObject.Position = position
}