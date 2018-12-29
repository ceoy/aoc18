package com.ceoy.aoc

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

class Day4 {
    companion object {
        private fun getDateDiffInMin(date1: Date, date2: Date): Long {
            val diffInMillis = date2.time - date1.time
            return TimeUnit.MINUTES.convert(diffInMillis, TimeUnit.MILLISECONDS)
        }
    }

    private val input: List<Bla> = FileLoader.load("day4.txt").map { Bla.parse(it) }.sortedBy { it.date }

    fun one() {
        val guardList = mutableListOf<Guard>()
        Guard.findSleepDuration(input) {
            guardList.add(Guard.parse(it))
        }

        // combine into one list
        val l = guardList.groupBy { it.id }.map {
            val t = it.value.sumBy { it.asleep.toInt() }
            val m = it.value.flatMap { it.dates }
            Guard(it.key, t.toLong(), m)
        }.maxBy {
            it.asleep
        }

        l?.let {
            val t = it.dates
                .flatMap {
                    getAllMinutes(it)
                }
                .groupBy {
                    it
                }
                .map {
                    Pair(it.key, it.value.size)
                }.maxBy {
                    it.second
                }

            println(t!!.first * it.id)
        }
    }

    fun two() {

        val guardList = mutableListOf<Guard>()
        Guard.findSleepDuration(input) {
            guardList.add(Guard.parse(it))
        }

        // combine into one list
        val l = guardList.groupBy { it.id }.map {
            val t = it.value.sumBy { it.asleep.toInt() }
            val m = it.value.flatMap { it.dates }
            Guard(it.key, t.toLong(), m)
        }

        l.let { guardList ->
            val guard = guardList.map { guard ->
                // check his most frequent time
                Pair(guard.id, guard.dates
                    .flatMap { it ->
                        getAllMinutes(it)
                    }.groupBy { it }
                    .map {
                        Pair(it.key, it.value.size)
                    }.maxBy { it.second })
            }.maxBy { it ->
                it.second?.second ?: 0
            }

            guard?.let { it ->
                val s = it.second?.first ?: 0
                println(it.first * s)
            }
        }
    }

    fun getAllMinutes(startAndEnd: Pair<Int, Int>): List<Int> {
        val mutableList = mutableListOf<Int>()
        for (i in startAndEnd.first..startAndEnd.second) {
            mutableList.add(i)
        }
        return mutableList
    }

    data class Bla(val date: Date, val input: String) {
        companion object {
            private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
            fun parse(input: String): Bla {
                val sub = input.substring(input.indexOf('[') + 1, input.indexOf(']'))
                val date = formatter.parse(sub)
                return Bla(date, input)
            }
        }
    }

    data class Guard(val id: Int, val asleep: Long, val dates: List<Pair<Int, Int>>) {
        companion object {
            fun parse(bla: List<Bla>): Guard {
                val idInput = bla.first().input

                val id = idInput.substring(idInput.indexOf('#') + 1, idInput.indexOf("begins shift")).trim().toInt()
                var startSleep: Date? = null
                var timeAsleep = 0L
                val asleepMinutes = mutableListOf<Pair<Int, Int>>()
                val c = Calendar.getInstance()
                bla.forEach {
                    if (it.input.contains("falls asleep")) {
                        startSleep = it.date
                    }

                    if (it.input.contains("wakes up")) {
                        startSleep?.let { start ->
                            timeAsleep += getDateDiffInMin(start, it.date)
                            c.time = start
                            val startMin = c.get(Calendar.MINUTE)
                            c.time = it.date
                            val endMin = c.get(Calendar.MINUTE)
                            asleepMinutes.add(Pair(startMin, endMin))
                        }
                        startSleep = null
                    }
                }

                return Guard(id, timeAsleep, asleepMinutes)
            }

            fun findSleepDuration(input: List<Bla>, block: (bla: List<Bla>) -> Unit) {
                val bla: MutableList<Bla> = mutableListOf()

                input.forEach {
                    // find start block, find end block
                    if (it.input.contains("begins shift") && bla.isNotEmpty()) {
                        block(bla)
                        bla.clear()
                    }

                    bla.add(it)
                }

                block(bla)
            }
        }
    }
}
