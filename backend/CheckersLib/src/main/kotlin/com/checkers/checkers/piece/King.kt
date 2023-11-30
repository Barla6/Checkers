package com.checkers.checkers.piece

import com.checkers.checkers.*
import com.checkers.utlis.blueString
import com.checkers.utlis.redString

class King(pieceColor: PieceColor) : Piece(pieceColor) {
    override fun toString(): String {
        return when (pieceColor) {
            PieceColor.BLACK -> redString("K")
            PieceColor.WHITE -> blueString("K")
        }
    }

    override fun clone() = King(pieceColor)

    override fun getDirections(): List<Direction> {
        return Direction.values().toList()
    }

    override fun possibleImmediateSteps(board: Board, from: Coordinate, hasEaten: Boolean): List<Step> {
        return getDirections().flatMap { direction ->
            val coordinateLine = from.coordinatesInDirection(direction)
            coordinateLine.mapNotNull { landingCoordinate ->

                val canSimpleStep = canSimpleStep(from, landingCoordinate, board)
                val canEat = canEat(from, landingCoordinate, board)

                when {
                    !hasEaten && canSimpleStep -> Step(
                        from = from,
                        to = landingCoordinate,
                        boardAfterMove = board.clone().apply { executeStep(from, landingCoordinate) },
                        hasEaten = false
                    )
                    canEat -> Step(
                        from = from,
                        to = landingCoordinate,
                        boardAfterMove = board.clone().apply { executeStep(from, landingCoordinate) },
                        hasEaten = true
                    )
                    else -> null
                }
            }
        }
    }

    fun canSimpleStep(from:Coordinate, landingCoordinate: Coordinate, board: Board): Boolean {
        val coordinatesBetween = Coordinate.between(from, landingCoordinate).getOrNull()!!
        val piecesInRange = board.piecesInRange(coordinatesBetween)
        val canLand = board[landingCoordinate] == null
        val noPiecesInRange = piecesInRange.isEmpty()
        return canLand && noPiecesInRange
    }

    fun canEat(from: Coordinate, landingCoordinate: Coordinate, board: Board): Boolean {
        val coordinatesBetween = Coordinate.between(from, landingCoordinate).getOrNull()!!
        val piecesInRange = board.piecesInRange(coordinatesBetween)
        val enemyExistsInRange = piecesInRange.size == 1 && piecesInRange.single().enemyOf(this)
        val canLand = board[landingCoordinate] == null
        return canLand && enemyExistsInRange
    }

    override fun equals(other: Any?): Boolean {
        if (other !is King) return false
        return pieceColor == other.pieceColor
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
