package io.github.tacascer.hanab.infer

sealed interface Clue {
    val turnIssued: Int
    val affectedCards: List<Card>
    val from: Name
    val to: Name
}

data class ColorClue(
    val color: CardColor,
    override val turnIssued: Int,
    override val affectedCards: List<Card>,
    override val from: Name,
    override val to: Name,
) : Clue

data class NumberClue(
    val number: Int,
    override val turnIssued: Int,
    override val affectedCards: List<Card>,
    override val from: Name,
    override val to: Name,
) : Clue
