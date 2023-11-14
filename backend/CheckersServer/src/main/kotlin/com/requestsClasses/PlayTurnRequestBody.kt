package com.requestsClasses

import com.checkers.models.Coordinates

data class PlayTurnRequestBody(val turnProgress: List<Coordinates>, val gameId: Int, val playerId: Int) : RequestBody() {
    override fun isValid(): Boolean {
        return turnProgress != null
                && gameId != null
                && playerId != null
    }
}