package com.management

import com.checkers.models.Game

object GamesManager {
    private val games = mutableMapOf<Int, Game>()
    private var serialNumber = 0

    fun registerGame(game: Game): Int {
        val newGameId = serialNumber
        games[newGameId] = game
        serialNumber++
        return newGameId
    }

    fun getGame(id: Int): Game? = games[id]
}