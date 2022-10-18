package com.management

import com.checkers.models.AIPlayer
import com.checkers.models.GameLevel
import com.checkers.models.Player

object PlayersManager {
    private val players = mutableMapOf<Int, Player>()
    private var serialNumber: Int

    init {
        GameLevel.values().forEach { gameLevel ->
            players[gameLevel.ordinal] = AIPlayer(gameLevel, gameLevel.levelName).apply { id = gameLevel.ordinal }
        }
        serialNumber = players.size
    }
    fun savePlayer(player: Player): Int {
        val newPlayerId = serialNumber
        player.id = newPlayerId
        players[newPlayerId] = player
        serialNumber++
        return newPlayerId
    }

    fun getPlayer(playerId: String): Player? = players[playerId.toInt()]

    fun getAIPlayer(level: String) : AIPlayer = (players[GameLevel.getGameLevelByLevelName(level)!!.ordinal] as AIPlayer).clone()

}