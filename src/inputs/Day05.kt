package inputs

import readText

private data class Procedure(val count: Int, val from: Int, val to: Int)

fun main() {
    fun String.getStackedCrates(): List<ArrayDeque<Char>> {
        val linesOfCrates = substringBefore("${System.lineSeparator()}${System.lineSeparator()}")
            .split(System.lineSeparator())
            .map {
                it.chunked(4).map { crate -> crate[1] }
            }
        val maxSize = linesOfCrates.last().size
        val stacks = List<ArrayDeque<Char>>(maxSize) { ArrayDeque(listOf()) }
        linesOfCrates.forEach { line ->
            line.forEachIndexed { i, crate ->
                if (crate != ' ') stacks[i].addFirst(crate)
            }
        }
        return stacks
    }

    fun String.getProcedure(): List<Procedure> {
        return substringAfter("${System.lineSeparator()}${System.lineSeparator()}")
            .split(System.lineSeparator())
            .map {
                val count = it.substringAfter("move ")
                    .substringBefore(" from")
                    .toInt()
                val from = it.substringAfter("from ")
                    .substringBefore(" to")
                    .toInt()
                val to = it.substringAfter("to ")
                    .toInt()
                Procedure(count, from, to)
            }
    }

    fun part1(input: String): String {
        val stacks = input.getStackedCrates()

        input.getProcedure().forEach { procedure ->
            repeat(procedure.count) {
                val toMove = stacks[procedure.from - 1].removeLast()
                stacks[procedure.to - 1].addLast(toMove)
            }
        }

        return stacks.map { it.last() }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readText("Day05_test")
    check(part1(testInput) == "CMZ")

    val input = readText("Day05")
    println(part1(input))
}