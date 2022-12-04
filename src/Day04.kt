fun main() {
    fun String.getAssignment(): Pair<Int, Int> =
        substringBefore("-").toInt() to substringAfter("-").toInt()

    fun isAssignmentContainingOther(assignment1: Pair<Int, Int>, assignment2: Pair<Int, Int>): Boolean {
        if (assignment1.first <= assignment2.first && assignment1.second >= assignment2.second) return true
        if (assignment2.first <= assignment1.first && assignment2.second >= assignment1.second) return true
        return false
    }

    fun part1(input: List<String>): Int {
        return input.map {
            isAssignmentContainingOther(
                it.substringBefore(",").getAssignment(),
                it.substringAfter(",").getAssignment()
            )
        }.count { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)

    val input = readInput("Day04")
    println(part1(input))
}