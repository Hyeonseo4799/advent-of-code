import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            line.substringAfter(":")
                .replace("|", "")
                .split(" ")
                .filter { it.isNotEmpty() }
                .groupBy { it }
                .filter { it.value.size > 1 }
                .let { 2.0.pow((it.size - 1).toDouble()) }
                .toInt()
        }
    }

    val testInput = readInput("Day04_test1")
    check(part1(testInput) == 13)

    val input = readInput("Day04")
    part1(input).println()
}

