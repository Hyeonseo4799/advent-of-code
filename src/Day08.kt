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

    val testInput = readInput("Day08_test1")
    check(part1(testInput) == 6)

    val input = readInput("Day08")
    part1(input).println()
}
