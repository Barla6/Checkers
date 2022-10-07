package com.requestsClasses

import com.checkers.models.Coordinates

data class PossibleMovesRequestBody (val gameId: String, val coordinates: Coordinates)