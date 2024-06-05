package io.github.tacascer.hanab.infer

import io.github.tacascer.hanab.engine.randomCard
import io.github.tacascer.hanab.engine.randomGame
import io.github.tacascer.hanab.engine.randomNumberClue
import io.github.tacascer.hanab.engine.randomPlayer
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.maps.shouldHaveKey
import io.kotest.matchers.maps.shouldNotHaveKey
import io.kotest.property.Arb
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.next
import io.kotest.property.checkAll

class InferEngineTest : FunSpec({

    context("Clued past") {
        test("Given a random game, when a clue is given, if the clue is not for the next player, then the next player is clued past") {
            checkAll(
                randomGame(
                    players =
                        listOf(
                            randomPlayer(hand = Arb.list(randomCard(), 5..5).next()).next(),
                            randomPlayer(hand = Arb.list(randomCard(), 5..5).next()).next(),
                            randomPlayer(hand = Arb.list(randomCard(), 5..5).next()).next(),
                        ),
                ),
            ) { game ->
                // Given
                val clue = randomNumberClue(from = game.players[0].name, to = game.players[2].name).next()

                // When
                val inferences = InferEngine().infer(game, clue)

                // Then
                inferences shouldHaveKey game.nextPlayer
                inferences.getValue(game.nextPlayer) shouldContain Inferences.CLUED_PAST
            }
        }

        test("Given a random game, when a clue is given, if the clue is for the next player, then the next player is not clued past") {
            checkAll(
                randomGame(
                    players =
                        listOf(
                            randomPlayer(hand = Arb.list(randomCard(), 5..5).next()).next(),
                            randomPlayer(hand = Arb.list(randomCard(), 5..5).next()).next(),
                            randomPlayer(hand = Arb.list(randomCard(), 5..5).next()).next(),
                        ),
                ),
            ) { game ->
                // Given
                val clue = randomNumberClue(from = game.currentPlayer.name, to = game.nextPlayer.name).next()

                // When
                val inferences = InferEngine().infer(game, clue)

                // Then
                inferences shouldNotHaveKey game.nextPlayer
            }
        }
    }
})
