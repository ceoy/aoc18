package com.ceoy.aoc

class Day7 {
    private var input: List<String> = FileLoader.load("day7.txt")

    fun one(stringList: List<String> = input): String {

        val steps = mutableMapOf<Char, MutableList<Char>>()
        stringList.forEach {
            val stepId = it.substring(5, 6)[0]
            val unlocks = it.substring(36, 37)[0]

            if (steps[stepId] == null) {
                steps[stepId] = mutableListOf()
            }
            if (steps[unlocks] == null) {
                steps[unlocks] = mutableListOf()
            }

            steps[stepId]?.add(unlocks)
        }

        var result = ""

        while (steps.isNotEmpty()) {
            val nextSteps = steps.filter {
                var success = false
                steps.forEach { key, list ->
                    if (list.contains(it.key)) {
                        success = true
                    }
                }
                !success
            }.toSortedMap()

            val resultKey = nextSteps.firstKey()
            result += resultKey
            steps.remove(resultKey)
        }

        return result
    }

    fun two(stringList: List<String> = input, increment: Int = 60, amountOfWorker: Int = 5): Int {

        val steps = mutableMapOf<Char, MutableList<Char>>()
        stringList.forEach {
            val stepId = it.substring(5, 6)[0]
            val unlocks = it.substring(36, 37)[0]

            if (steps[stepId] == null) {
                steps[stepId] = mutableListOf()
            }
            if (steps[unlocks] == null) {
                steps[unlocks] = mutableListOf()
            }

            steps[stepId]?.add(unlocks)
        }

        var result = -1
        var resultString = ""
        val allTheWorker = mutableListOf<Worker>()
        for (i in 0 until amountOfWorker) {
            allTheWorker.add(Worker())
        }

        var gameOver = false
        while (!gameOver) {
            result++
            if (steps.isEmpty()) {
                gameOver = true
                continue
            }

            val nextSteps = steps.filter {
                var success = true
                steps.forEach { _, list ->
                    if (list.contains(it.key)) {
                        success = false
                    }
                }
                success
            }.toSortedMap()

            // check if a worker is available
            nextSteps.forEach { key, _ ->
                if (allTheWorker.any { it.workingOn == key }) {
                    return@forEach
                }

                val worker = allTheWorker.firstOrNull {
                    !it.working
                }

                worker?.let {
                    it.working = true
                    it.timeWorking = (key - 64).toInt() + increment
                    it.workingOn = key
                    it.done = {
                        resultString += key
                        steps.remove(key)
                    }
                }
            }

            allTheWorker.forEach { it.doWork() }
        }

        return result
    }

    data class Worker(
        var workingOn: Char = ' ',
        var working: Boolean = false,
        var timeWorking: Int = 0,
        var done: () -> Unit = {}
    ) {
        fun doWork() {
            if (working) {
                timeWorking -= 1
                if (timeWorking <= 0) {
                    working = false
                    workingOn = ' '
                    done()
                }
            }
        }
    }
}
