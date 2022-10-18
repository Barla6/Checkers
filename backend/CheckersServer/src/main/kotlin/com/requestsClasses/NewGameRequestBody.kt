package com.requestsClasses

import com.checkers.models.GameLevel

data class NewGameRequestBody(val level: String, val playerName: String) {
    fun isValid(): Boolean {
        return level != null
                && GameLevel.getGameLevelByLevelName(level) != null
                && playerName != null
    }
}