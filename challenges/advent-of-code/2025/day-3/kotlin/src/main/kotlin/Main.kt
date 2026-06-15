package com.kyleerhabor

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import java.io.File

data class Joltage(val high: Int, val low: Int) : Comparable<Joltage> {
    val value = high * 10 + low

    override fun compareTo(other: Joltage) = this.value.compareTo(other.value)
}

fun banks(input: String): List<List<Int>> = input
    .trimEnd()
    .lines()
    .map { bank -> bank.map { it.digitToInt() } }

fun day1(input: String): String {
    val banks = banks(input)
    val joltage = banks
        .map { bank -> // 4278
            val iterator = bank.listIterator()
            val joltage = iterator.asSequence().fold(
                Joltage(high = iterator.next(), low = iterator.next())
            ) { joltage, battery ->
                val joltage1 = Joltage(high = joltage.high, low = battery) // 47, 48
                val joltage2 = Joltage(high = joltage.low, low = battery) // 27, 78
                val joltage = maxOf(joltage, joltage1, joltage2)

                joltage
            }

            joltage
        }
        .sumOf { it.value }
        .toString()

    return joltage
}

private const val JOLTAGE_BATTERY_COUNT = 12

fun day2(input: String): String {
    val banks = banks(input)
    val joltage = banks
        .map { bank ->
            val iterator = bank.iterator()
            val batteries = iterator.asSequence().take(JOLTAGE_BATTERY_COUNT).toList()
            require(batteries.size == JOLTAGE_BATTERY_COUNT) { "Expected 12 joltage batteries" }

            val joltage = iterator.asSequence().fold(batteries.toIntArray()) { joltage, battery ->
                val candidate = joltage.toList() + battery
                val index = when {
                    candidate[0] < candidate[1] -> 0
                    candidate[1] < candidate[2] -> 1
                    candidate[2] < candidate[3] -> 2
                    candidate[3] < candidate[4] -> 3
                    candidate[4] < candidate[5] -> 4
                    candidate[5] < candidate[6] -> 5
                    candidate[6] < candidate[7] -> 6
                    candidate[7] < candidate[8] -> 7
                    candidate[8] < candidate[9] -> 8
                    candidate[9] < candidate[10] -> 9
                    candidate[10] < candidate[11] -> 10
                    candidate[11] < candidate[12] -> 11
                    else -> 12
                }

                val joltage = (candidate.slice(0 until index) + candidate.slice(index + 1..candidate.lastIndex))
                    .toIntArray()

                joltage
            }

            joltage
        }
        .sumOf { joltage ->
            joltage.fold(0L) { a, battery -> a * 10 + battery }
        }
        .toString()

    return joltage
}

class Cli : CliktCommand() {
    val input: File by option().file().required()

    override fun run() {
        val input = this.input.readText()

        println("Day 1: ${day1(input = input)}")
        println("Day 2: ${day2(input = input)}")
    }
}

fun main(args: Array<String>) = Cli().main(args)