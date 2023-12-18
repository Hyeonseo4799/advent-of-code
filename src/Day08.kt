fun main() {
    fun part1(input: List<String>): Int {
        val instructions = input.first()
        val node = input.drop(2).associate { line ->
            line.replace(" ", "").let {
                val key = it.substringBefore("=")
                val value = it.substringAfter("=").trim('(', ')').split(",")
                key to (value.first() to value.last())
            }
        }

        var letter = "AAA"
        var count = 0

        while (letter != "ZZZ") {
            instructions.forEach { letter = if (it == 'L') node[letter]!!.first else node[letter]!!.second }
            count += instructions.length
        }

        return count
    }

    fun part2(input: List<String>): Long {
        val instructions = input.first()
        val node = input.drop(2).associate { line ->
            line.replace(" ", "").let {
                val key = it.substringBefore("=")
                val value = it.substringAfter("=").trim('(', ')').split(",")
                key to (value.first() to value.last())
            }
        }

        return input.asSequence().drop(2)
            .map { it.substringBefore(" =") }
            .filter { it.endsWith('A') }
            .map { startNode ->
                var letter = startNode
                var count = 0

                while (!letter.endsWith('Z')) {
                    instructions.forEach { letter = if (it == 'L') node[letter]!!.first else node[letter]!!.second }
                    count += instructions.length
                }
                count
            }
            .map(Int::toLong)
            .toList()
            .let { it.fold(it.first()) { current, number -> lcm(current, number) } }
    }

    val testInput = readInput("Day08_test1")
    val testInput2 = readInput("Day08_test2")
    check(part1(testInput) == 6)
    check(part2(testInput2) == 6L)

    val input = readInput("Day08")
    part2(input).println()
    part1(input).println()
}

private fun gcd(a: Long, b: Long): Long = if (b != 0L) gcd(b, a % b) else a

private fun lcm(a: Long, b: Long): Long = a * b / gcd(a, b)
