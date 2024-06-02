package io.github.tacascer.hanab.infer

import io.kotest.core.spec.style.FunSpec
import io.kotest.property.checkAll

class PlayerTest : FunSpec({
    test("when a player is given a number clue, the player should have that clue in their numberClues list") {
        checkAll(randomPlayer, randomNumberClue) { player, clue ->
            val playerWithClue = player.addClue(clue)
            playerWithClue.numberClues.contains(clue)
        }
    }

    test("when a player is given a color clue, the player should have that clue in their colorClues list") {
        checkAll(randomPlayer, randomColorClue) { player, clue ->
            val playerWithClue = player.addClue(clue)
            playerWithClue.colorClues.contains(clue)
        }
    }
})
