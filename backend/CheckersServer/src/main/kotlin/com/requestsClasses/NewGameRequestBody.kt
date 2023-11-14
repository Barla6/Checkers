package com.requestsClasses

import com.checkers.models.GameLevel

data class NewGameRequestBody(val level: String, val playerName: String) : RequestBody() {
    override fun isValid(): Boolean {
        return level != null
                && playerName != null
    }
}