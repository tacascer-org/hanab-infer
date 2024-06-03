package io.github.tacascer.hanab.infer

data class Player(
    private val name: String,
    private val hand: List<Card>,
    val numberClues: List<NumberClue> = listOf(),
    val colorClues: List<ColorClue> = listOf(),
) {
    fun addClue(clue: NumberClue): Player {
        return Player(name, hand, numberClues + clue, colorClues)
    }

    fun addClue(clue: ColorClue): Player {
        return Player(name, hand, numberClues, colorClues + clue)
    }
}
