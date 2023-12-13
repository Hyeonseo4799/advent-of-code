fun main() {
    fun part1(input: List<String>): Long {
        val seeds = input.first()
            .split(" ")
            .mapNotNull { it.toLongOrNull() }

        val mapsList = input.joinToString("\n")
            .split("\n\n")
            .drop(1)
            .map { it.lines().drop(1) }
            .map { maps ->
                maps.map { numbers ->
                    numbers.split(" ")
                        .map(String::toLong)
                        .let { (first, second, third) ->
                            Triple(first, second, third)
                        }
                }
            }

        return seeds.minOf { seed ->
            mapsList.fold(seed) { current, maps ->
                maps.find { current in it.second..(it.second + it.third) }
                    ?.let {
                        val gap = current - it.second
                        it.first + gap
                    } ?: current
            }
        }
    }

    val testInput = readInput("Day05_test1")
    check(part1(testInput) == 35L)

    val input = readInput("Day05")
    part1(input).println()
}

