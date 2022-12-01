fun main() {
    fun part1(input: String): Long {
        return input.split("${System.lineSeparator()}${System.lineSeparator()}")
            .map { foodByElf ->
                foodByElf.split(System.lineSeparator())
                    .map { foodItem -> foodItem.toLong() }
                    .reduce { acc, item -> acc + item }
            }.max()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readText("Day01_test").trim()
    check(part1(testInput) == 24000L)

    val input = readText("Day01").trim()
    println(part1(input))
}
