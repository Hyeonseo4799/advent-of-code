fun main() {
    fun part1(input: List<String>): Int {
        val pipes = input.map { it.toList() }
        val startPosition = input.indexOfFirst { 'S' in it }.let { input[it].indexOf('S') to it }
        val movements = mapOf(
            ('|' to Direction.S) to Direction.S,
            ('|' to Direction.N) to Direction.N,
            ('-' to Direction.E) to Direction.E,
            ('-' to Direction.W) to Direction.W,
            ('L' to Direction.W) to Direction.N,
            ('L' to Direction.S) to Direction.E,
            ('J' to Direction.E) to Direction.N,
            ('J' to Direction.S) to Direction.W,
            ('7' to Direction.E) to Direction.S,
            ('7' to Direction.N) to Direction.W,
            ('F' to Direction.W) to Direction.S,
            ('F' to Direction.N) to Direction.E
        )

        return Direction.entries.map { startDirection ->
            var position = startPosition
            var direction = startDirection
            val path = mutableListOf<Pair<Int, Int>>()

            do {
                path.add(position)
                position = position.next(direction)
                direction = movements[pipes.getOrNull(position.second)?.getOrNull(position.first) to direction] ?: break
            } while (position != startPosition)

            position to path
        }
            .find { (position, _) -> position == startPosition }
            ?.let { (_, path) -> path.size / 2 } ?: 0
    }

    val testInput = readInput("Day10_test1")
    check(part1(testInput) == 4)

    val input = readInput("Day10")
    part1(input).println()
}

enum class Direction {
    E, W, S, N
}

private fun Pair<Int, Int>.next(direction: Direction): Pair<Int, Int> {
    return when (direction) {
        Direction.E -> this.first + 1 to this.second
        Direction.W -> this.first - 1 to this.second
        Direction.S -> this.first to this.second + 1
        Direction.N -> this.first to this.second - 1
    }
}
