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

    fun part2(input: List<String>): Int {
        val instances = MutableList(input.size) { 1 }

        input.mapIndexed { currentIdx, line ->
            val cards = line.substringAfter(":")
                .replace("|", "")
                .split(" ")
                .filter { it.isNotEmpty() }
                .groupBy { it }
                .filter { it.value.size > 1 }
                .values

            cards.forEachIndexed { index, _ ->
                val cardIdx = currentIdx + (index + 1)
                instances[cardIdx] += instances[currentIdx]
            }
        }
        return instances.sum()
    }

    val testInput = readInput("Day04_test1")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

