package com.responsesClasses

import com.checkers.models.Coordinates

data class PossibleMoveToSend(val step: Coordinates, val board: BoardToSend, val eaten: Boolean, val completed: Boolean)