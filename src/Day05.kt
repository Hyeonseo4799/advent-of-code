import kotlin.math.max
import kotlin.math.min

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

    fun part2(input: List<String>): Long {
        var seeds = input
            .first()
            .split(" ")
            .mapNotNull { it.toLongOrNull() }
            .chunked(2)
            .map { it[0] to (it[0] + it[1] - 1) }
            .toMutableList()

        input.joinToString("\n")
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
            .forEach { maps ->
                val newList = mutableListOf<Pair<Long, Long>>()
                maps.forEach { (dest, source, length) ->
                    seeds.filter {
                        it.first <= (source + length - 1) && it.second >= source
                    }.forEach { matching ->
                        val range = max(matching.first, source) to min(matching.second, source + length - 1)
                        val difference = range.first - source to range.second - source
                        val matchedRange = difference.first + dest to difference.second + dest

                        if (matching.first < range.first) {
                            seeds += matching.first to (range.first - 1)
                        }

                        if (matching.second > range.second) {
                            seeds += (range.second + 1) to matching.second
                        }

                        newList += matchedRange
                        seeds.remove(matching)
                    }
                }
                newList.addAll(seeds)
                seeds = newList
            }
        return seeds.flatMap { it.toList() }.min()
    }

    val testInput = readInput("Day05_test1")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
