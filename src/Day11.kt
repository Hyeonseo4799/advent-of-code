import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val galaxies = input.flatMapIndexed { y, line ->
            line.mapIndexed { x, c -> c.takeIf { it == '#' }?.let { x to y } }.filterNotNull()
        }
        val expandedX = input.indices.filter { !input[it].contains('#') }
        val expandedY = input[0].indices.filter { !input.map { line: String -> line[it] }.contains('#') }

        return galaxies.flatMap { index ->
            val others = galaxies.filter { it != index }

            others.zip(List(others.size) { index }).map { (p1, p2) ->
                val xCount = expandedY.count { it in min(p1.first, p2.first)..max(p1.first, p2.first) }
                val yCount = expandedX.count { it in min(p1.second, p2.second)..max(p1.second, p2.second) }

                abs(p2.first - p1.first) + xCount + abs(p2.second - p1.second) + yCount
            }
        }.let { it.sum() / 2 }
    }

    fun part2(input: List<String>): Long {
        val galaxies = input.flatMapIndexed { y, line ->
            line.mapIndexed { x, c -> c.takeIf { it == '#' }?.let { x to y } }.filterNotNull()
        }
        val expandedX = input.indices.filter { !input[it].contains('#') }
        val expandedY = input[0].indices.filter { !input.map { line: String -> line[it] }.contains('#') }

        return galaxies.flatMap { index ->
            val others = galaxies.filter { it != index }

            others.zip(List(others.size) { index }).map { (p1, p2) ->
                val xCount = expandedY.count { it in min(p1.first, p2.first)..max(p1.first, p2.first) } * (1000000 - 1L)
                val yCount = expandedX.count { it in min(p1.second, p2.second)..max(p1.second, p2.second) } * (1000000 - 1L)

                (abs(p2.first - p1.first) + xCount + abs(p2.second - p1.second) + yCount)
            }
        }.let { it.sum() / 2 }
    }

    val testInput = readInput("Day11_test1")
    check(part1(testInput) == 374)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
