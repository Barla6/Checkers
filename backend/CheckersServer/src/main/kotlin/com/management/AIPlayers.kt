package com.management

import com.checkers.models.AIPlayer
import com.checkers.models.GameLevel
import com.checkers.models.Player

object AIPlayers {
    private val players = mutableMapOf<String,Player>()

    init {
        players["easy"] = AIPlayer(GameLevel.EASY, "easy").apply { id = "easy" }
        players["medium"] = AIPlayer(GameLevel.MEDIUM, "medium").apply { id = "medium" }
        players["hard"] = AIPlayer(GameLevel.HARD, "hard").apply { id = "hard" }
    }

    fun getPlayer(level: String) : AIPlayer = (players[level] as AIPlayer).clone()
}