import kotlin.math.floor

private enum class Operation { MULTIPLY, ADD, SQUARE }
private data class Monkey(
    val items: MutableList<Long> = mutableListOf(),
    val operation: Pair<Operation, Int> = Pair(Operation.ADD, 0),
    val testDivider: Int = 1,
    val monkeyTrue: Int = -1,
    val monkeyFalse: Int = -1
)

fun main() {
    fun List<String>.monkeyList(): List<Monkey> {
        val monkeys = mutableListOf<Monkey>()
        var monkey = Monkey()
        forEach { line ->
            val trimmed = line.trim()
            if (trimmed.isEmpty()) {
                monkeys.add(monkey)
            } else if (trimmed.startsWith("Starting items: ")) {
                val items = trimmed.substringAfter(": ")
                    .split(", ")
                    .map { it.toLong() }
                    .toMutableList()
                monkey = monkey.copy(items = items)
            } else if (trimmed.startsWith("Operation: ")) {
                val (opType, opValue) = trimmed.split(" ").takeLast(2)
                monkey = when (opType) {
                    "+" -> monkey.copy(operation = Pair(Operation.ADD, opValue.toInt()))
                    "*" ->
                        if (opValue == "old")
                            monkey.copy(operation = Pair(Operation.SQUARE, 1))
                        else
                            monkey.copy(operation = Pair(Operation.MULTIPLY, opValue.toInt()))

                    else -> throw IllegalArgumentException()
                }
            } else if (trimmed.startsWith("Test: "))
                monkey = monkey.copy(testDivider = trimmed.split(" ").last().toInt())
            else if (trimmed.startsWith("If true: "))
                monkey = monkey.copy(monkeyTrue = trimmed.split(" ").last().toInt())
            else if (trimmed.startsWith("If false: "))
                monkey = monkey.copy(monkeyFalse = trimmed.split(" ").last().toInt())
            else if (trimmed.startsWith("Monkey")) {
                monkey = Monkey()
            } else throw IllegalArgumentException()
        }
        monkeys.add(monkey)
        return monkeys
    }

    fun List<Monkey>.visit(): Long {
        val visits = MutableList(size) { 0 }
        repeat(20) {
            for (i in indices) {
                val items = this[i].items
                val newItems = items.map { item ->
                    val worry = when (this[i].operation.first) {
                        Operation.MULTIPLY -> item * this[i].operation.second
                        Operation.ADD -> item + this[i].operation.second
                        Operation.SQUARE -> item * item
                    }
                    val newItem = floor(worry / 3.0).toLong()
                    val testTrue = newItem % this[i].testDivider == 0L
                    newItem to testTrue
                }
                this[this[i].monkeyTrue].items.addAll(newItems.filter { it.second }.map { it.first })
                this[this[i].monkeyFalse].items.addAll(newItems.filter { !it.second }.map { it.first })
                visits[i] = visits[i] + this[i].items.size
                this[i].items.clear()
            }
        }
        val (max1, max2) = visits.sortedDescending().take(2)
        return max1.toLong() * max2.toLong()
    }

    fun List<Monkey>.visitPart2(): Long {
        val totalDivider = this.map { it.testDivider }.reduce { acc, d -> acc * d }
        val visits = MutableList(size) { 0 }
        repeat(10000) {
            for (i in indices) {
                val items = this[i].items
                val newItems = items.map { item ->
                    var remainder = item
                    when (this[i].operation.first) {
                        Operation.MULTIPLY -> {
                            remainder *= this[i].operation.second
                            if (remainder > totalDivider) {
                                remainder %= totalDivider
                            }
                        }

                        Operation.ADD -> {
                            remainder += this[i].operation.second
                            if (remainder > totalDivider) {
                                remainder -= totalDivider
                            }
                        }

                        Operation.SQUARE -> {
                            remainder *= remainder
                        }
                    }
                    val testTrue = remainder % this[i].testDivider == 0L
                    remainder to testTrue
                }
                this[this[i].monkeyTrue].items.addAll(newItems.filter { it.second }.map { it.first })
                this[this[i].monkeyFalse].items.addAll(newItems.filter { !it.second }.map { it.first })
                visits[i] = visits[i] + this[i].items.size
                this[i].items.clear()
            }
        }
        val (max1, max2) = visits.sortedDescending().take(2)
        return max1.toLong() * max2.toLong()
    }

    fun part1(input: List<String>): Long {
        return input.monkeyList().visit()
    }

    fun part2(input: List<String>): Long {
        return input.monkeyList().visitPart2()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158L)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}