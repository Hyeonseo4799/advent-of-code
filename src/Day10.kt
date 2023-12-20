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

    fun part2(input: List<String>): Int {
        val pipes = input.map { it.toMutableList() }
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
        val shape = Direction.entries.map { startDirection ->
            var position = startPosition
            var direction = startDirection
            val path = mutableListOf<Pair<Int, Int>>()

            do {
                path.add(position)
                position = position.next(direction)
                direction = movements[pipes.getOrNull(position.second)?.getOrNull(position.first) to direction] ?: break
            } while (position != startPosition)

            Triple(position, path, (startDirection to direction))
        }
            .find { (position, _, _) -> position == startPosition }
            ?.let { (_, path, directions) ->
                val startPipe = (directions.first to directions.second.opposite).toPipe()
                pipes[startPosition.second][startPosition.first] = startPipe
                path
            }

        return pipes.flatMapIndexed { y, line ->
            List(line.size) { x ->
                if (shape?.contains(x to y) == true) return@List false

                var intersects = 0

                for (offset in 1..Int.MAX_VALUE) {
                    val next = (x + offset) to y

                    if (pipes.getOrNull(next.second)?.getOrNull(next.first) == null) break

                    if (shape?.contains(next) == true && pipes[next.second][next.first] !in listOf('L', '-', 'J')) {
                        intersects++
                    }
                }
                intersects % 2 == 1
            }
        }.count { it }
    }

    val testInput = readInput("Day10_test1")
    val testInput2 = readInput("Day10_test2")
    check(part1(testInput) == 4)
    check(part2(testInput2) == 4)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}

enum class Direction {
    E, W, S, N;

    val opposite by lazy {
        when (this) {
            E -> W
            W -> E
            S -> N
            N -> S
        }
    }
}

private fun Pair<Int, Int>.next(direction: Direction): Pair<Int, Int> {
    return when (direction) {
        Direction.E -> this.first + 1 to this.second
        Direction.W -> this.first - 1 to this.second
        Direction.S -> this.first to this.second + 1
        Direction.N -> this.first to this.second - 1
    }
}

private fun Pair<Direction, Direction>.toPipe(): Char {
    return when (this) {
        Direction.S to Direction.N, Direction.N to Direction.S -> '|'
        Direction.E to Direction.W, Direction.W to Direction.E -> '-'
        Direction.N to Direction.E, Direction.E to Direction.N -> 'L'
        Direction.N to Direction.W, Direction.W to Direction.N -> 'J'
        Direction.S to Direction.W, Direction.W to Direction.S -> '7'
        Direction.S to Direction.E, Direction.E to Direction.S -> 'F'
        else -> error("Unknown Pipe")
    }
}
