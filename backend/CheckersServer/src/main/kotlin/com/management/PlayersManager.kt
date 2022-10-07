package com.management

import com.checkers.models.Player

object PlayersManager {
    private val players = mutableMapOf<Int, Player>()
    private var serialNumber = AIPlayers.players.size

    fun savePlayer(player: Player): Int {
        val newPlayerId = serialNumber
        player.id = newPlayerId
        players[newPlayerId] = player
        serialNumber++
        return newPlayerId
    }
}