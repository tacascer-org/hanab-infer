package io.github.tacascer.hanab.engine

/**
 * Represents the game state of a Hanabi game.
 *
 * @param players The players in the game.
 * @param currentPlayer The current player.
 */
data class Game(
    val players: List<Player>,
    val currentPlayer: Player = players.first(),
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
     * A map from player to their position in the players list.
     */
    private val playerToPlayerPositionMap = players.mapIndexed { index, player -> player to index }.toMap()

    /**
     * The next player to play.
     */
    val nextPlayer =
        playerToPlayerPositionMap.getValue(currentPlayer).let { players[(it + 1) % players.size] }

    private val nameToPlayerMap = players.associateBy { it.name }
}
