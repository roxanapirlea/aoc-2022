fun main() {
    fun getOutcomeScore(you: Int, opponent: Int): Int {
        return when ((you - opponent).mod(3)) {
            1 -> 6
            2 -> 0
            0 -> 3
            else -> throw IllegalArgumentException()
        }
    }

    fun String.toRPSScore(): Int =
        when (this) {
            "A", "X" -> 1
            "B", "Y" -> 2
            "C", "Z" -> 3
            else -> throw IllegalArgumentException()
        }

    fun part1(input: List<String>): Long {
        return input.map {
            val opponent = it.substringBefore(" ").toRPSScore()
            val me = it.substringAfter(" ").toRPSScore()
            val outcome = getOutcomeScore(me, opponent)
            outcome + me.toLong()
        }.reduce { total, score -> total + score }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15L)

    val input = readInput("Day02")
    println(part1(input))
}