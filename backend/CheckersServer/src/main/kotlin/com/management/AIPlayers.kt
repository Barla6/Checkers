package com.management

import com.checkers.models.AIPlayer
import com.checkers.models.GameLevel
import com.checkers.models.Player

object AIPlayers {
    val players = mutableMapOf<String,Player>()

    init {
        players["easy"] = AIPlayer(GameLevel.EASY, "easy").apply { id = 0 }
        players["medium"] = AIPlayer(GameLevel.MEDIUM, "medium").apply { id = 1 }
        players["hard"] = AIPlayer(GameLevel.HARD, "hard").apply { id = 2 }
    }

    fun getPlayer(level: String) : AIPlayer = (players[level] as AIPlayer).clone()
}