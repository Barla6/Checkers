package com.management

import com.checkers.models.Player

object PlayersManager {
    private val players = mutableMapOf<Int, Player>()
    private var serialNumber: Int = 0

    fun registerPlayer(player: Player):Int {
        player.id = serialNumber
        players[serialNumber] = player
        serialNumber++
        return player.id
    }

    fun getPlayer(index: Int): Player? {
        return players[index]
    }

}