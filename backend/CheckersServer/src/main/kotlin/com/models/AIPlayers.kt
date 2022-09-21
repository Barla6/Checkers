package com.models

import com.checkers.models.AIPlayer
import com.checkers.models.GameLevel
import com.checkers.models.Player

object AIPlayers {
    val players = mutableMapOf<String,Player>()

    init {
        players["easy"] = AIPlayer(GameLevel.EASY, "easy")
        players["medium"] = AIPlayer(GameLevel.MEDIUM, "medium")
        players["hard"] = AIPlayer(GameLevel.HARD, "hard")
    }
}