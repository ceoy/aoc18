package com.ceoy.aoc

import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    // Day1().run { measure { one() }; measure { two() } }
    // Day2().run { measure { one() }; measure { two() } }
    // Day3().run { measure { one() }; measure { two() } }
    // Day4().run { measure { one() }; measure { two() } }

    Day5().run { measure { one() }; measure { two() } }
}

/**
 * Measure time for execution
 */
fun measure(block: () -> Unit) {
    val duration = measureTimeMillis { block() }
    println("Duration: $duration ms")
}
