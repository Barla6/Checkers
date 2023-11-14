package com.requestsClasses

import com.checkers.models.Coordinates
import com.responsesClasses.BoardToSend

data class PossibleMovesRequestBody (val board: BoardToSend, val coordinates: Coordinates, val eaten: Boolean, val turnProgress: List<Coordinates>) : RequestBody() {
    override fun isValid(): Boolean {
        return board != null
                && coordinates != null
                && eaten != null
                && turnProgress != null
    }
}