package com.checkers.models.piece

import com.checkers.models.*
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
            val coordinateLine = from.getAllCoordinatesInDirection(direction)
            var canGoFurther: Boolean
            coordinateLine.mapNotNull { landingCoordinate ->

                val piecesInRange = board.piecesInRange(Coordinate.between(from, landingCoordinate))

                canGoFurther = board[landingCoordinate]?.pieceColor != pieceColor || piecesInRange.size > 1
                if (!canGoFurther) return@mapNotNull null

                when {
                    !hasEaten && canSimpleStep(landingCoordinate, board, piecesInRange) -> Step(
                        from = from,
                        to = landingCoordinate,
                        boardAfterMove = board.clone().apply { executeStep(from, landingCoordinate) },
                        hasEaten = false
                    )
                    canEat(landingCoordinate, board, piecesInRange) -> Step(
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

    fun canSimpleStep(landingCoordinate: Coordinate, board: Board, piecesInRange: List<Piece>): Boolean {
        val canLand = board[landingCoordinate] == null
        val noPiecesInRange = piecesInRange.isEmpty()
        return canLand && noPiecesInRange
    }

    private fun canEat(landingCoordinate: Coordinate, board: Board, piecesInRange: List<Piece>): Boolean {
        val enemyExistsInRange = piecesInRange.size == 1 && piecesInRange.single().enemyOf(this)
        val canLand = board[landingCoordinate] == null
        return canLand && enemyExistsInRange
    }
}
