package io.github.tacascer.hanab.infer

data class Card(
    val value: Int,
    val color: CardColor,
) {
    init {
        require(value in 1..5) { "Card value must be between 1 and 5" }
    }
}

enum class CardColor {
    RED,
    YELLOW,
    GREEN,
    BLUE,
    PURPLE,
}
