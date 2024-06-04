package io.github.tacascer.hanab.infer

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.next

class GameTest : FunSpec({
    test("can create game") {
        shouldNotThrowAny {
            randomGame.next()
        }
    }

    test("can't create game with less than 2 players") {
        val exception =
            shouldThrow<IllegalArgumentException> { Game(Arb.list(randomPlayer(0), 1..1).next()) }
        exception.message shouldBe "Hanabi is a game for 2-5 players"
    }

    test("can't create game with more than 5 players") {
        val exception =
            shouldThrow<IllegalArgumentException> {
                Game(Arb.list(randomPlayer(0), 6..10).next())
            }
        exception.message shouldBe "Hanabi is a game for 2-5 players"
    }

    context("clued past") {
        test("when a clue is given, if the clue is not for the next player, then infer that the next player has been clued past") {
            val game = randomGame(4).next()
            val players = game.players
            val clue =
                randomNumberClue.next().copy(
                    from = players[0].name,
                    to = players[2].name,
                )

            val gameWithClue = game.addClue(clue)

            gameWithClue.inferences[players[1]].shouldContainExactly(Inferences.CLUED_PAST)
        }

        test("when a clue is given, if the clue is for the next player, then don't infer that the next player has been clued past") {
            val game = randomGame(4).next()
            val players = game.players
            val clue =
                randomNumberClue.next().copy(
                    from = players[0].name,
                    to = players[1].name,
                )

            val gameWithClue = game.addClue(clue)

            gameWithClue.inferences[players[1]] shouldBe null
        }
    }
})
