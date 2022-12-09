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

    fun Point.move(dir: String): Point = when (dir) {
        "R" -> copy(x = x + 1)
        "L" -> copy(x = x - 1)
        "U" -> copy(y = y + 1)
        "D" -> copy(y = y - 1)
        else -> throw IllegalArgumentException()
    }

    fun Point.moveAfter(head: Point): Point {
        val newTail = if (abs(head.x - x) <= 1 && abs(head.y - y) <= 1)
            this
        else if (head.x - x == 0) {
            if (head.y - y == 2)
                copy(y = y + 1)
            else
                copy(y = y - 1)
        } else if (head.y - y == 0) {
            if (head.x - x == 2)
                copy(x = x + 1)
            else
                copy(x = x - 1)
        } else {
            if (head.x - x > 0 && head.y - y > 0)
                copy(x = x + 1, y = y + 1)
            else if (head.x - x < 0 && head.y - y < 0)
                copy(x = x - 1, y = y - 1)
            else if (head.x - x > 0 && head.y - y < 0)
                copy(x = x + 1, y = y - 1)
            else
                copy(x = x - 1, y = y + 1)
        }
        return newTail
    }

    fun move(rope: Rope, dir: String): Rope {
        val newHead = rope.h.move(dir)
        val newTail = rope.t.moveAfter(newHead)
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

    fun part2(input: List<String>): Int {
        val coveredPos = mutableSetOf<Point>()
        val bigRope = MutableList(10) { Point(0, 0) }
        coveredPos.add(bigRope.last())
        getMoves(input).forEach { move ->
            repeat(move.steps) {
                bigRope[0] = bigRope[0].move(move.dir)
                for (i in 1 until bigRope.size) {
                    bigRope[i] = bigRope[i].moveAfter(bigRope[i-1])
                }
                coveredPos.add(bigRope.last())
            }
        }
        return coveredPos.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)

    val testInput2 = readInput("Day09_test2")
    check(part2(testInput2) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}