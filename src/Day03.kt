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

    val testInput = readInput("Day03_test1")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
}

