package com.requestsClasses

import com.checkers.models.Coordinates
import com.responsesClasses.BoardToSend

data class PossibleMovesRequestBody (val board: BoardToSend, val coordinates: Coordinates, val eaten: Boolean)