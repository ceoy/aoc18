package com.ceoy.aoc

fun Int.toDigitList(): List<Int> {
    val digitArray = mutableListOf<Int>()
    var copy = this

    do {
        val toRemove = copy % 10
        digitArray.add(0, toRemove)
        copy -= toRemove
        copy /= 10
    } while (copy > 0)

    return digitArray
}

fun <E> List<E>.takeAt(n: Int, startIndex: Int): List<E> {
    return this.subList(startIndex, startIndex + n)
}

fun Char.toNumber(): Int {
    return Character.getNumericValue(this)
}

fun List<Int>.toLong(): Long = this.joinToString("").toLong()