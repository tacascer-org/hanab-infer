package io.github.tacascer.hanab.engine

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.property.checkAll

class PlayerTest : FunSpec({
    test("when a player is given a number clue, the player should have that clue in their numberClues list") {
        checkAll(randomPlayer(numberClues = emptyList()), randomNumberClue()) { player, clue ->
            val playerWithClue = player.addClue(clue)
            playerWithClue.numberClues shouldContain clue
        }
    }

    test("when a player is given a color clue, the player should have that clue in their colorClues list") {
        checkAll(randomPlayer(colorClues = emptyList()), randomColorClue()) { player, clue ->
            val playerWithClue = player.addClue(clue)
            playerWithClue.colorClues shouldContain clue
        }
    }
})
