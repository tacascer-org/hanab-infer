package io.github.tacascer.hanab.infer

/**
 * A game of Hanabi.
 * @param players The players in the game. Is a list because the play order is important.
 * @param inferences The inferences that have been made in the game.
 * @param currentPlayer The index of the current player in the players list.
 * @param turn The current turn number.
 */
data class Game(
    val players: List<Player>,
    val inferences: Map<Player, List<Inferences>> = emptyMap(),
    private val currentPlayer: Player = players.first(),
    private val turn: Int = 0,
) {
    init {
        require(players.size in 2..5) { "Hanabi is a game for 2-5 players" }
        if (players.size in 2..3) {
            require(players.all { it.handSize == 5 }) { "Players in a 2-3 player game must have 5 cards" }
        } else {
            require(players.all { it.handSize == 4 }) { "Players in a 4-5 player game must have 4 cards" }
        }
        require(players.distinctBy { it.name }.size == players.size) { "Players must have unique names" }
    }

    /**
     * Add a clue to the game.
     */
    fun addClue(clue: Clue): Game {
        val updatedInferences = inferCluedPast(clue, inferences)

        return copy(inferences = updatedInferences)
    }

    /**
     * Infer that the next player has been clued past if the clue is not for them.
     * @param clue The clue that was given.
     * @param currentInferences The current inferences.
     * @return The updated inferences.
     */
    private fun inferCluedPast(
        clue: Clue,
        currentInferences: Map<Player, List<Inferences>>,
    ): Map<Player, List<Inferences>> {
        val to = clue.to
        val toPlayer = nameToPlayerMap.getValue(to)
        return if (toPlayer == nextPlayer) {
            currentInferences
        } else {
            currentInferences + (nextPlayer to (currentInferences[nextPlayer] ?: emptyList()) + Inferences.CLUED_PAST)
        }
    }

    /**
     * A map from player to their position in the players list.
     */
    private val playerToPlayerPositionMap = players.mapIndexed { index, player -> player to index }.toMap()

    /**
     * The next player to play.
     */
    private val nextPlayer =
        playerToPlayerPositionMap.getValue(currentPlayer).let { players[(it + 1) % players.size] }

    private val nameToPlayerMap = players.associateBy { it.name }
}

/**
 * Inferences that can be made from the game.
 */
enum class Inferences {
    CLUED_PAST,
}
