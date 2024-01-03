fun main() {
    fun part1(input: List<String>): Int {
        val records = input.map { it.split(" ") }.map { it.first() to it.last().split(",").map(String::toInt) }

        return records.sumOf { (condition, size) ->
            val unknown = condition.mapIndexedNotNull { index, c -> if (c == '?') index else null }
            val broken = condition.mapIndexedNotNull { index, c -> if (c == '#') index else null }
            val remain = size.sum() - broken.size
            val subsets = unknown.getSubsets().filter { it.size == remain }.map { it + broken }

            subsets.count {
                List(it.max() + 1) { index -> if (index in it) '#' else '.' }
                    .joinToString("")
                    .split(".")
                    .filter(String::isNotEmpty)
                    .map(String::length) == size
            }
        }
    }

    val testInput = readInput("Day12_test1")
    check(part1(testInput) == 21)

    val input = readInput("Day12")
    part1(input).println()
}

private fun List<Int>.getSubsets(): List<List<Int>> {
    return if (this.isEmpty()) listOf(emptyList())
    else drop(1).getSubsets().let { it + it.map { subset -> subset + this.first() } }
}
