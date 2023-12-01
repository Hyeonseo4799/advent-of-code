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

    fun part2(input: List<String>): Int {
        val numbers = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
            .mapIndexed { index, number -> (index + 1) to number }

        val converted = input.map { line ->
            line.fold("") { current, next ->
                val digit = "$current$next"
                numbers.find { (_, number) -> digit.contains(number) }
                    ?.let { (value, number) -> digit.replace(number, "$value$next")}
                    ?: "$current$next"
            }
        }
        return part1(converted)
    }


    val testInput1 = readInput("Day01_test1")
    val testInput2 = readInput("Day01_test2")
    check(part1(testInput1) == 142)
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
