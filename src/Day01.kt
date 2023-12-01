fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            line.asSequence()
                .mapNotNull(Char::digitToIntOrNull)
                .joinToString(separator = "")
                .let { "${it.first()}${it.last()}" }
                .toInt()
        }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
}
