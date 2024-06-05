package io.github.tacascer.hanab.infer

import io.github.tacascer.hanab.engine.Clue
import io.github.tacascer.hanab.engine.Game
import io.github.tacascer.hanab.engine.Player

class InferEngine {
    fun infer(
        game: Game,
        clue: Clue,
    ): Map<Player, List<Inferences>> {
        return if (clue.to != game.nextPlayer.name) {
            mapOf(game.nextPlayer to listOf(Inferences.CLUED_PAST))
        } else {
            emptyMap()
        }
    }
}

enum class Inferences {
    CLUED_PAST,
}
