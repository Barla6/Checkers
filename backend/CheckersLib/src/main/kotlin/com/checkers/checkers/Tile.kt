package com.checkers.checkers

import com.checkers.checkers.piece.Piece

class Tile(val coordinate: Coordinate, var piece: Piece? = null) : Cloneable {

    fun isBlack(): Boolean {
        return coordinate.col % 2 == (coordinate.row + 1) % 2
    }

    override fun toString(): String {
        return piece?.toString() ?: " "
    }

    public override fun clone() = Tile(coordinate.clone(), piece?.clone())

    override fun equals(other: Any?): Boolean {
        if (other !is Tile) return false
        return coordinate == other.coordinate && piece == other.piece
    }

    override fun hashCode(): Int {
        var result = coordinate.hashCode()
        result = 31 * result + (piece?.hashCode() ?: 0)
        return result
    }
}