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

fun day1(input: String): String {
    val banks = input
        .trimEnd()
        .lines()
        .map { bank -> bank.map { it.digitToInt() } }

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

fun day2(input: String): String {
    TODO()
}

class Cli : CliktCommand() {
    val input: File by option().file().required()

    override fun run() {
        val input = this.input.readText()

        println("Day 1: ${day1(input = input)}")
//        println("Day 2: ${day2(input = input)}")
    }
}

fun main(args: Array<String>) = Cli().main(args)