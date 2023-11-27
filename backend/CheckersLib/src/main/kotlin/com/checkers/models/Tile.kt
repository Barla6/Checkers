package com.checkers.models

import com.checkers.models.piece.Piece

class Tile(val coordinate: Coordinate, var piece: Piece? = null) : Cloneable {

    fun isBlack(): Boolean {
        return coordinate.col % 2 == (coordinate.row + 1) % 2
    }

    override fun toString(): String {
        return piece?.toString() ?: " "
    }

    public override fun clone() = Tile(coordinate.clone(), piece?.clone())
}