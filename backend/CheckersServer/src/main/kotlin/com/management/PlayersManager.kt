package com.management

import com.checkers.models.Player

object PlayersManager {
    private val players = mutableMapOf<Int, Player>()
    private var serialNumber = 0

    fun savePlayer(player: Player): Int {
        val newPlayerId = serialNumber
        player.id = newPlayerId.toString()
        players[newPlayerId] = player
        serialNumber++
        return newPlayerId
    }
}