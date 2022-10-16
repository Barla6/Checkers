package com.responsesClasses

import com.checkers.models.Board
import com.checkers.models.Coordinates

data class PossibleMoveToSend(val sequence: List<Coordinates>, val board: Board)