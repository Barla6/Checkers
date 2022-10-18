package com.requestsClasses

import com.checkers.models.Coordinates

data class PlayTurnRequestBody(val steps: List<Coordinates>, val gameId: String, val playerId: String) {
    fun isValid(): Boolean {
        return steps != null
                && steps.size >= 2
                && gameId != null
                && playerId != null
    }
}