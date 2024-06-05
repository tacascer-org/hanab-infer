package io.github.tacascer.hanab.engine

sealed interface Clue {
    val from: Name
    val to: Name
}

data class ColorClue(
    override val from: Name,
    override val to: Name,
    val color: CardColor,
) : Clue

data class NumberClue(
    override val from: Name,
    override val to: Name,
    val number: Int,
) : Clue
