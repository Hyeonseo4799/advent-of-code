fun main() {
    data class Hand(val card: List<Int>, val bid: Int)

    fun part1(input: List<String>): Int {
        return input.map { it.split(" ") }
            .map {
                val mapping = listOf('T', 'J', 'Q', 'K', 'A')
                Hand(
                    card = it[0].toList().map { c -> if (c.isLetter()) mapping.indexOf(c) + 10 else c.digitToInt() },
                    bid = it[1].toInt()
                )
            }
            .groupBy { hand ->
                val sameCards = hand.card.groupBy { it }.values.map { it.size }
                val isFullHouse = sameCards.all { it == 2 || it == 3 }
                val isTwoPair = sameCards.count { it == 2 } == 2

                sameCards.max()
                    .let { if (isFullHouse || isTwoPair) it + 0.5 else it }
                    .toDouble()
            }
            .toSortedMap()
            .map { (_, hands) ->
                hands.sortedWith(compareBy(
                    { it.card[0] }, { it.card[1] }, { it.card[2] }, { it.card[3] }, { it.card[4] }
                ))
            }
            .flatten()
            .mapIndexed { index, hand -> hand.bid * (index + 1) }
            .sum()
    }

    val testInput = readInput("Day07_test1")
    check(part1(testInput) == 6440)

    val input = readInput("Day07")
    part1(input).println()
}
