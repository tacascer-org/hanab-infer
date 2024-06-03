package io.github.tacascer.hanab.infer

sealed interface Clue {
    val turnIssued: Int
    val affectedCards: List<Card>
}

data class ColorClue(
    val color: CardColor,
    override val turnIssued: Int,
    override val affectedCards: List<Card>,
) : Clue

data class NumberClue(
    val number: Int,
    override val turnIssued: Int,
    override val affectedCards: List<Card>,
) : Clue
