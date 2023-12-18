fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            line.split(" ")
                .map(String::toInt)
                .let { numbers ->
                    val differenceList = mutableListOf(numbers)

                    while (differenceList.last().any { it != 0 }) {
                        val current = differenceList.last()
                        val newDifference = current.zipWithNext { a, b -> b - a }

                        differenceList.add(newDifference)
                    }
                    differenceList
                }
                .reversed()
                .fold(0) { sum, numbers -> sum + numbers.last() }
                .toInt()
        }
    }

    val testInput = readInput("Day09_test1")
    check(part1(testInput) == 114)

    val input = readInput("Day09")
    part1(input).println()
}
