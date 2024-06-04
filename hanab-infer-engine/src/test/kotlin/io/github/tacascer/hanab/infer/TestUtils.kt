package io.github.tacascer.hanab.infer

import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.enum
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.merge
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.string
import io.kotest.property.arbitrary.uuid

val randomPlayer =
    arbitrary { rs ->
        val name = Arb.string().next(rs)
        val hand = Arb.list(randomCard).next(rs)
        Player(name, hand)
    }

/**
 * Generates a random player with a hand of [handSize] cards.
 */
fun randomPlayer(handSize: Int): Arb<Player> {
    return arbitrary { rs ->
        val name = Arb.uuid().next(rs).toString()
        val hand = Arb.list(randomCard, handSize..handSize).next(rs)
        Player(name, hand)
    }
}

val randomCard =
    arbitrary { rs ->
        val value = Arb.int(1..5)
        val color = Arb.enum<CardColor>()
        Card(value.next(rs), color.next(rs))
    }

val randomNumberClue =
    arbitrary { rs ->
        val turnIssued = Arb.int(0..10).next(rs)
        val affectedCards = Arb.list(randomCard).next(rs)
        val numberClue = Arb.int(1..5).next(rs)
        val from = Arb.string().next(rs)
        val to = Arb.string().next(rs)
        NumberClue(numberClue, turnIssued, affectedCards, from, to)
    }

val randomColorClue =
    arbitrary { rs ->
        val turnIssued = Arb.int(0..10).next(rs)
        val affectedCards = Arb.list(randomCard).next(rs)
        val colorClue = Arb.enum<CardColor>().next(rs)
        val from = Arb.string().next(rs)
        val to = Arb.string().next(rs)
        ColorClue(colorClue, turnIssued, affectedCards, from, to)
    }

/**
 * Generates a random game with 2 to 5 players.
 *
 * If the game has 2 to 3 players, the hand size of each player is 5.
 * If the game has 4 to 5 players, the hand size of each player is 4.
 */
val randomGame =
    arbitrary { rs ->
        val randomPlayers = Arb.list(randomPlayer(5), 2..3).merge(Arb.list(randomPlayer(4), 4..5)).next(rs)
        Game(randomPlayers)
    }

/**
 * Generates a random game with [noOfPlayers] players.
 */
fun randomGame(noOfPlayers: Int): Arb<Game> =
    arbitrary { rs ->
        if (noOfPlayers in 2..3) {
            val randomPlayers = Arb.list(randomPlayer(5), noOfPlayers..noOfPlayers).next(rs)
            Game(randomPlayers)
        } else {
            val randomPlayers = Arb.list(randomPlayer(4), noOfPlayers..noOfPlayers).next(rs)
            Game(randomPlayers)
        }
    }
