package com.checkers.checkers

class Step(
    val from: Coordinate,
    val to: Coordinate,
    val hasEaten: Boolean,
    val boardAfterMove: Board,
) {

    override fun equals(other: Any?): Boolean {
        if (other !is Step) return false
        return from == other.from &&
                to == other.to &&
                hasEaten == other.hasEaten &&
                boardAfterMove == other.boardAfterMove
    }

    override fun hashCode(): Int {
        var result = from.hashCode()
        result = 31 * result + to.hashCode()
        result = 31 * result + hasEaten.hashCode()
        result = 31 * result + boardAfterMove.hashCode()
        return result
    }
}