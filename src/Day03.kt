fun main() {
    fun Char.getPriority(): Long {
        return if (this.isUpperCase()) {
            code - 'A'.code + 27L
        } else {
            code - 'a'.code + 1L
        }
    }

    fun part1(input: List<String>): Long {
        return input.map { backpack ->
            val (compartment1, compartment2) =
                backpack.chunked(backpack.length / 2).map { it.toCharArray().toSet() }
            val common = compartment1.intersect(compartment2).single()
            common.getPriority()
        }.reduce { total, priority -> total + priority }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157L)

    val input = readInput("Day03")
    println(part1(input))
}