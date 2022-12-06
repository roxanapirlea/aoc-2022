fun main() {
    fun getStartMarker(input: String, windowSize: Int): Int {
        var step = 0
        input.windowed(windowSize)
            .forEach {
                step++
                if (it.toCharArray().toSet().size == windowSize) return step + windowSize - 1
            }
        return step + windowSize - 1
    }

    fun part1(input: String): Int {
        return getStartMarker(input, 4)
    }

    fun part2(input: String): Int {getStartMarker(input, 14)
        return getStartMarker(input, 14)
    }

    // test if implementation meets criteria from the description
    check(part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 7)
    check(part1("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(part1("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)

    check(part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 19)
    check(part2("bvwbjplbgvbhsrlpgdmjqwftvncz") == 23)
    check(part2("nppdvjthqldpwncqszvftbrmjlhg") == 23)
    check(part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 29)
    check(part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 26)

    val input = readText("Day06")
    println(part1(input))
    println(part2(input))
}