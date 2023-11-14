package com.requestsClasses

class GetTurnRequestBody(val gameId: Int, val playerId: Int) : RequestBody() {

    override fun isValid(): Boolean {
        return gameId != null
                && playerId != null
    }
}