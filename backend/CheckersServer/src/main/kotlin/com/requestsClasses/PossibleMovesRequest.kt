package com.requestsClasses

import com.checkers.models.Coordinates

data class PossibleMovesRequest(val gameId: String, val coordinates: Coordinates)