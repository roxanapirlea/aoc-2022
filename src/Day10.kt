fun main() {
    fun List<String>.toInstr(): List<Pair<Int, Int>> =
        map {
            val instrCount = when {
                it.startsWith("noop") -> 1
                it.startsWith("addx") -> 2
                else -> throw IllegalArgumentException()
            }
            val instrVal = when {
                it.startsWith("noop") -> 0
                it.startsWith("addx") -> it.substringAfter(" ").toInt()
                else -> throw IllegalArgumentException()
            }
            instrCount to instrVal
        }


    fun part1(input: List<String>): Int {
        var cycle = 0
        var sigStrength = 0
        var partialSum = 1
        var remarcableCycle = 20
        val remarcableCycleRepetition = 40
        input.toInstr().forEach { (instrCount, instrVal) ->
            if (cycle < remarcableCycle && cycle + instrCount >= remarcableCycle) {
                sigStrength += partialSum * remarcableCycle
                remarcableCycle += remarcableCycleRepetition
            }
            partialSum += instrVal
            cycle += instrCount
        }
        return sigStrength
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)

    val input = readInput("Day10")
    println(part1(input))
}