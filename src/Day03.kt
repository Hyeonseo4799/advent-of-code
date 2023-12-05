fun main() {
    data class Number(val value: String, val x: Int, val y: Int)

    fun part1(input: List<String>): Int {
        return input.flatMapIndexed { y, line ->
            """\d+""".toRegex()
                .findAll(line)
                .map { Number(it.value, it.range.first, y) }
        }.sumOf { (value, x, y) ->
            input.withIndex()
                .filter {
                    it.index in (y - 1)..(y + 1)
                }
                .flatMap {
                    val startX = (x - 1).coerceAtLeast(0)
                    val endX = (startX + (value.length + 1)).coerceAtMost(it.value.length - 1)
                    it.value.slice(startX..endX).toList()
                }
                .let {
                    val isSymbol = it.any { c -> !c.isDigit() && c != '.' }
                    if (isSymbol) value.toInt() else 0
                }
        }
    }

    fun part2(input: List<String>): Int {
        return input.flatMapIndexed { y, line ->
            """\d+""".toRegex()
                .findAll(line)
                .map { Number(it.value, it.range.first, y) }
        }.let { numbers ->
            input.mapIndexed { y, line ->
                line.withIndex()
                    .filter { it.value == '*' }
                    .map {
                        val x = it.index
                        numbers.filter { number ->
                            val numberX = listOf(number.x, number.x + number.value.lastIndex)

                            number.y in (y - 1)..(y + 1) && numberX.any { c -> c in (x - 1)..(x + 1) }
                        }
                    }
                    .filter { it.size == 2 }
                    .takeIf { it.isNotEmpty() }
                    ?.let { near ->
                        near.sumOf { it[0].value.toInt() * it[1].value.toInt() }
                    }
            }
        }
            .filterNotNull()
            .sum()
    }

    val testInput = readInput("Day03_test1")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)


    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

