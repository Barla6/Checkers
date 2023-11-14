package com.responsesClasses

import com.checkers.models.Coordinates

data class PossibleMoveToSend(val targetCoordinates: Coordinates, val board: BoardToSend, val eaten: Boolean, val completed: Boolean)