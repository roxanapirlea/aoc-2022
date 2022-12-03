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

    fun part2(input: List<String>): Long {
        return input.chunked(3)
            .map { group -> group.map { backpack -> backpack.toCharArray().toSet() } }
            .map { group ->
                val common = group.reduce { common, backpack -> common intersect backpack }.single()
                common.getPriority()
            }.reduce { total, priority -> total + priority }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157L)
    check(part2(testInput) == 70L)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}