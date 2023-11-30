package com.checkers.checkers.errors

import com.checkers.checkers.Coordinate
import com.checkers.checkers.PieceColor

class NoPieceToEat private constructor(message: String?) : Throwable(message) {

    constructor(pieceColor: PieceColor, coordinate: Coordinate):
            this("Not found piece of color: $pieceColor to eat on: $coordinate")
}