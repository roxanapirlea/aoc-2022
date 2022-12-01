fun main() {
    fun getFoodByElf(input: String): List<Long> =
        input.split("${System.lineSeparator()}${System.lineSeparator()}")
            .map { foodByElf ->
                foodByElf.split(System.lineSeparator())
                    .map { foodItem -> foodItem.toLong() }
                    .reduce { acc, item -> acc + item }
            }

    fun part1(input: String): Long {
        return getFoodByElf(input).max()
    }

    fun part2(input: String): Long {
        return getFoodByElf(input).sortedDescending().take(3).reduce { acc, food -> acc + food }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readText("Day01_test").trim()
    check(part1(testInput) == 24000L)
    check(part2(testInput) == 45000L)

    val input = readText("Day01").trim()
    println(part1(input))
    println(part2(input))
}
