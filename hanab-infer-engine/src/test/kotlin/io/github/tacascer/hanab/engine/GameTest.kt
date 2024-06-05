package io.github.tacascer.hanab.engine

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.next

class GameTest : FunSpec({
    test("can't create game with fewer than 2 players") {
        val exception =
            shouldThrow<IllegalArgumentException> { Game(Arb.list(randomPlayer(), 1..1).next()) }

        exception.message shouldBe "Hanabi is a game for 2-5 players"
    }

    test("can't create game with more than 5 players") {
        val exception =
            shouldThrow<IllegalArgumentException> {
                Game(Arb.list(randomPlayer(), 6..10).next())
            }

        exception.message shouldBe "Hanabi is a game for 2-5 players"
    }

    test("given a game with 2 to 3 players whose hand size is not 5, when create game, then throw IllegalArgumentException") {
        val exception =
            shouldThrow<IllegalArgumentException> {
                Game(
                    Arb.list(randomPlayer(hand = Arb.list(randomCard(), 0..4).next()), 2..3).next(),
                )
            }

        exception.message shouldBe "Players in a 2-3 player game must have 5 cards"
    }

    test("given a game with 4 to 5 players whose hand size is not 4, when create game, then throw IllegalArgumentException") {
        val exception =
            shouldThrow<IllegalArgumentException> {
                Game(
                    Arb.list(randomPlayer(hand = Arb.list(randomCard(), 0..3).next()), 4..5).next(),
                )
            }

        exception.message shouldBe "Players in a 4-5 player game must have 4 cards"
    }

    test("given a game with duplicate player names, when create game, then throw IllegalArgumentException") {
        val exception =
            shouldThrow<IllegalArgumentException> {
                Game(
                    listOf(
                        Player("Alice", Arb.list(randomCard(), 5..5).next()),
                        Player("Alice", Arb.list(randomCard(), 5..5).next()),
                    ),
                )
            }

        exception.message shouldBe "Players must have unique names"
    }
})
