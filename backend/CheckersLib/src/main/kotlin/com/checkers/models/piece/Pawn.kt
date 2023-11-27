package com.checkers.models.piece

import com.checkers.models.*
import com.checkers.utlis.blueString
import com.checkers.utlis.redString

class Pawn(pieceColor: PieceColor) : Piece(pieceColor) {
    override fun toString(): String {
        return when (pieceColor) {
            PieceColor.BLACK -> redString("o")
            PieceColor.WHITE -> blueString("o")
        }
    }

    override fun clone() = Pawn(pieceColor)

    fun toKing() = King(pieceColor)

    override fun getDirections(): List<Direction> {
        return pieceColor.defaultDirections
    }

    override fun possibleImmediateSteps(board: Board, from: Coordinate, hasEaten: Boolean): List<Step> {
        return getDirections().mapNotNull { direction ->
            val simpleStepTarget = from.step(direction).getOrNull() ?: return@mapNotNull null
            val eatingStepTarget = from.step(direction, 2).getOrNull()

            when {
                !hasEaten && canSimpleStep(simpleStepTarget, board) -> Step(
                    from = from,
                    to = simpleStepTarget,
                    boardAfterMove = board.clone().apply { executeStep(from, to = simpleStepTarget) },
                    hasEaten = false
                )

                canEat(simpleStepTarget, eatingStepTarget, board) -> Step(
                    from = from,
                    to = eatingStepTarget!!,
                    boardAfterMove = board.clone().apply { executeStep(from, to = eatingStepTarget) },
                    hasEaten = true
                )

                else -> null
            }
        }
    }

    fun canSimpleStep(nextCoordinate: Coordinate, board: Board): Boolean {
        return board[nextCoordinate] == null
    }

//    fun canSimpleStep(from: Coordinate, direction: Direction, board: Board): Boolean {
//        val nextCoordinate = from.step(direction).getOrNull() ?: return false
//        return board[nextCoordinate] == null
//    }

    fun canEat(nextCoordinate: Coordinate, landingCoordinate: Coordinate?, board: Board): Boolean {
        val nextToEnemy = board[nextCoordinate]?.let { enemyOf(this) } ?: false
        val canLand = landingCoordinate != null && board[landingCoordinate] == null
        return nextToEnemy && canLand
    }

//    fun canEat(from: Coordinate, direction: Direction, board: Board): Boolean {
//        val nextCoordinate = from.step(direction).getOrNull() ?: return false
//        val landingCoordinate = from.step(direction, 2).getOrNull() ?: return false
//        val nextPiece = board[nextCoordinate] ?: return false
//        return nextPiece.enemyOf(this) && board[landingCoordinate] == null
//    }
}