import kotlin.math.max

private data class Tree(
    val height: Int,
    val maxLeft: Int = -1,
    val maxRight: Int = -1,
    val maxTop: Int = -1,
    val maxBottom: Int = -1
)

fun main() {
    fun List<String>.toTrees(): List<List<Tree>> {
        return map { line -> line.toCharArray().map { c -> Tree(c.digitToInt()) } }
    }

    fun List<List<Tree>>.getVisibleTrees(): List<List<Tree>> {
        val trees: MutableList<MutableList<Tree>> = mutableListOf()
        forEach { line -> trees.add(line.toMutableList()) }

        for (i in 0 until trees.size) {
            for (j in 0 until trees[i].size) {
                val maxTop = if (i == 0) -1 else max(trees[i - 1][j].maxTop, trees[i - 1][j].height)
                val maxLeft = if (j == 0) -1 else max(trees[i][j - 1].maxLeft, trees[i][j - 1].height)
                trees[i][j] = trees[i][j].copy(maxLeft = maxLeft, maxTop = maxTop)
            }
        }
        for (i in trees.size - 1 downTo 0) {
            for (j in trees[i].size - 1 downTo 0) {
                val maxBottom = if (i == trees.size - 1) -1 else max(trees[i + 1][j].maxBottom, trees[i + 1][j].height)
                val maxRight = if (j == trees[i].size - 1) -1 else max(trees[i][j + 1].maxRight, trees[i][j + 1].height)
                trees[i][j] = trees[i][j].copy(maxRight = maxRight, maxBottom = maxBottom)
            }
        }
        return trees
    }

    fun part1(input: List<String>): Int {
        return input.toTrees().getVisibleTrees().flatten().count {
            it.height > it.maxLeft || it.height > it.maxRight || it.height > it.maxTop || it.height > it.maxBottom
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)

    val input = readInput("Day08")
    println(part1(input))
}