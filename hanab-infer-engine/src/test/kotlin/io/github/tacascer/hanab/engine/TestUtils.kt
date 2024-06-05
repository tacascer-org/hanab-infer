package io.github.tacascer.hanab.engine

import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.enum
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.merge
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.string

/**
 * Generates a random card.
 */
fun randomCard(
    value: Int? = null,
    color: CardColor? = null,
): Arb<Card> {
    return arbitrary {
        Card(
            value ?: Arb.int(1..5).next(it),
            color ?: Arb.enum<CardColor>().next(it),
        )
    }
}

/**
 * Generates a random player.
 */
fun randomPlayer(
    name: String? = null,
    hand: List<Card>? = null,
    numberClues: List<NumberClue>? = null,
    colorClues: List<ColorClue>? = null,
): Arb<Player> {
    return arbitrary {
        Player(
            name ?: Arb.string().next(it),
            hand ?: Arb.list(randomCard(), 0..5).next(it),
            numberClues ?: Arb.list(randomNumberClue()).next(it),
            colorClues ?: Arb.list(randomColorClue()).next(it),
        )
    }
}

fun randomColorClue(
    from: String? = null,
    to: String? = null,
    color: CardColor? = null,
): Arb<ColorClue> {
    return arbitrary {
        ColorClue(
            from ?: Arb.string().next(it),
            to ?: Arb.string().next(it),
            color ?: Arb.enum<CardColor>().next(it),
        )
    }
}

fun randomNumberClue(
    from: String? = null,
    to: String? = null,
    number: Int? = null,
): Arb<NumberClue> {
    return arbitrary {
        NumberClue(
            from ?: Arb.string().next(it),
            to ?: Arb.string().next(it),
            number ?: Arb.int(1..5).next(it),
        )
    }
}

/**
 * Generates a random game.
 *
 * If the game has 2 to 3 players, the hand size of each player is 5.
 * If the game has 4 to 5 players, the hand size of each player is 4.
 */
fun randomGame(players: List<Player>? = null): Arb<Game> =
    arbitrary { rs ->
        Game(
            players ?: Arb
                .list(randomPlayer(hand = Arb.list(randomCard(), 5..5).next(rs)), 2..3)
                .merge(Arb.list(randomPlayer(hand = Arb.list(randomCard()).next(rs)), 4..5))
                .next(rs),
        )
    }
