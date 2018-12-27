package com.ceoy.aoc

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    println("Run ./gradlew test")

    val elapsedTime = measureTimeMillis {
        Day11(6548).partTwo()
    }

    println("elapsedTime: $elapsedTime")
}
