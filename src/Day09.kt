import kotlin.math.abs

private data class Point(val x: Int, val y: Int)
private data class Move(val steps: Int, val dir: String)
private data class Rope(val h: Point, val t: Point)

fun main() {
    fun getMoves(input: List<String>) =
        input.map { line ->
            val (d, s) = line.split(" ")
            Move(s.toInt(), d)
        }

    fun move(rope: Rope, dir: String): Rope {
        val newHead = when (dir) {
            "R" -> rope.h.copy(x = rope.h.x + 1)
            "L" -> rope.h.copy(x = rope.h.x - 1)
            "U" -> rope.h.copy(y = rope.h.y + 1)
            "D" -> rope.h.copy(y = rope.h.y - 1)
            else -> throw IllegalArgumentException()
        }
        val newTail = if (abs(newHead.x - rope.t.x) <= 1 && abs(newHead.y - rope.t.y) <= 1)
            rope.t
        else if (newHead.x - rope.t.x == 0) {
            if (newHead.y - rope.t.y == 2)
                rope.t.copy(y = rope.t.y + 1)
            else
                rope.t.copy(y = rope.t.y - 1)
        } else if (newHead.y - rope.t.y == 0) {
            if (newHead.x - rope.t.x == 2)
                rope.t.copy(x = rope.t.x + 1)
            else
                rope.t.copy(x = rope.t.x - 1)
        } else {
            if (newHead.x - rope.t.x > 0 && newHead.y - rope.t.y > 0)
                rope.t.copy(x = rope.t.x + 1, y = rope.t.y + 1)
            else if (newHead.x - rope.t.x < 0 && newHead.y - rope.t.y < 0)
                rope.t.copy(x = rope.t.x - 1, y = rope.t.y - 1)
            else if (newHead.x - rope.t.x > 0 && newHead.y - rope.t.y < 0)
                rope.t.copy(x = rope.t.x + 1, y = rope.t.y - 1)
            else
                rope.t.copy(x = rope.t.x - 1, y = rope.t.y + 1)
        }
        return Rope(newHead, newTail)
    }

    fun part1(input: List<String>): Int {
        val coveredPos = mutableSetOf<Point>()
        var rope = Rope(Point(0, 0), Point(0, 0))
        coveredPos.add(rope.t)
        getMoves(input).forEach { move ->
            repeat(move.steps) {
                rope = move(rope, move.dir)
                coveredPos.add(rope.t)
            }
        }
        return coveredPos.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)

    val input = readInput("Day09")
    println(part1(input))
}