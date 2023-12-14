fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            line.substringAfter(":")
                .split(" ")
                .filter(String::isNotEmpty)
                .map(String::toInt)
        }
            .let { (times, distances) -> times.zip(distances) }
            .map { (time, distance) ->
                (1..(time / 2))
                    .map { (time - it) * it }
                    .count { it > distance }
                    .let { if (time % 2 == 0) it * 2 - 1 else it * 2 }
            }
            .fold(1) { total, count -> total * count }
    }

    fun part2(input: List<String>): Long {
        return input.map { line ->
            line.substringAfter(":")
                .split(" ")
                .filter(String::isNotEmpty)
                .map(String::toLong)
        }
            .let { (times, distances) -> times.zip(distances) }
            .map { (time, distance) ->
                (1..(time / 2))
                    .map { (time - it) * it }
                    .count { it > distance }
                    .let { if (time % 2 == 0L) it * 2 - 1 else it * 2 }
            }
            .fold(1) { total, count -> total * count }
    }

    val testInput = readInput("Day06_test1")
    val testInput2 = readInput("Day06_test2")
    check(part1(testInput) == 288)
    check(part2(testInput2) == 71503L)

    val input = readInput("Day06_1")
    val input2 = readInput("Day06_2")
    part1(input).println()
    part2(input2).println()
}
