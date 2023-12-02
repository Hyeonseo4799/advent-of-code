fun main() {
    fun part1(input: List<String>, block: Triple<Int, Int, Int>): Int {
        return input.sumOf { game ->
            val max = game.substringAfter(":")
                .replace(" ", "")
                .split(";")
                .map { line ->
                    line.split(",").let { colors ->
                        Triple(
                            colors.find { it.contains("red") }
                                ?.substringBefore("red")?.toInt() ?: 0,
                            colors.find { it.contains("green") }
                                ?.substringBefore("green")?.toInt() ?: 0,
                            colors.find { it.contains("blue") }
                                ?.substringBefore("blue")?.toInt() ?: 0
                        )
                    }
                }.let { rgb ->
                    Triple(
                        rgb.maxBy { it.first }.first,
                        rgb.maxBy { it.second }.second,
                        rgb.maxBy { it.third }.third
                    )
                }

            if (block.first >= max.first && block.second >= max.second && block.third >= max.third)
                game.substringBefore(":").split(" ")[1].toInt()
            else
                0
        }
    }

    val testInput1 = readInput("Day02_test1")
    check(part1(testInput1, Triple(12, 13, 14)) == 8)

    val input = readInput("Day02")
    part1(input, Triple(12, 13, 14)).println()
}
