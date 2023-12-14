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


    val testInput = readInput("Day06_test1")
    check(part1(testInput) == 288)

    val input = readInput("Day06_1")
    part1(input).println()
}
