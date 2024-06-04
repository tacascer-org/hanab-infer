package io.github.tacascer.hanab.infer

data class Player(
    val name: String,
    private val hand: List<Card>,
    val numberClues: List<NumberClue> = listOf(),
    val colorClues: List<ColorClue> = listOf(),
) {
    fun addClue(clue: Clue): Player {
        return when (clue) {
            is NumberClue -> Player(name, hand, numberClues + clue, colorClues)
            is ColorClue -> Player(name, hand, numberClues, colorClues + clue)
        }
    }

    val handSize: Int
        get() = hand.size
}

typealias Name = String
