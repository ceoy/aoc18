package com.ceoy.aoc

import java.io.File

object FileLoader {
    fun load(fileName: String): List<String> {
        return File(javaClass.classLoader.getResource(fileName).toURI()).readLines()
    }
}