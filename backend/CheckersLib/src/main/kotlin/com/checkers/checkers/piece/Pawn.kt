package com.checkers.checkers.piece

import com.checkers.checkers.errors.LandingCoordinateIsTaken
import com.checkers.checkers.errors.NoPieceToEat
import com.checkers.checkers.*
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

    infix fun crowingRowIs(row: Int): Boolean {
        return row == pieceColor.crowningRow
    }

    override fun getDirections(): List<Direction> {
        return pieceColor.defaultDirections
    }

    override fun possibleImmediateSteps(board: Board, from: Coordinate, hasEaten: Boolean): List<Step> {
        return getDirections().mapNotNull { direction ->
            val simpleStepTarget = canSimpleStep(from, direction, board).getOrNull()
            val eatingStepTarget = canEat(from, direction, board).getOrNull()
            when {
                !hasEaten && simpleStepTarget != null -> Step(
                    from = from,
                    to = simpleStepTarget,
                    boardAfterMove = board.clone().apply { executeStep(from, to = simpleStepTarget) },
                    hasEaten = false
                )

                eatingStepTarget != null -> Step(
                    from = from,
                    to = eatingStepTarget,
                    boardAfterMove = board.clone().apply { executeStep(from, to = eatingStepTarget) },
                    hasEaten = true
                )

                else -> null
            }
        }
    }

    fun canSimpleStep(from: Coordinate, direction: Direction, board: Board): Result<Coordinate> = runCatching {
        val landingCoordinate = from.step(direction).getOrThrow()
        if (board[landingCoordinate] != null)
            throw LandingCoordinateIsTaken(landingCoordinate)
        landingCoordinate
    }

    fun canEat(from: Coordinate, direction: Direction, board: Board): Result<Coordinate> = runCatching {
        val nextCoordinate = from.step(direction).getOrThrow()
        val landingCoordinate = from.step(direction, 2).getOrThrow()
        val nextPiece = board[nextCoordinate]
        if (nextPiece == null || !nextPiece.enemyOf(this))
            throw NoPieceToEat(pieceColor.enemy, nextCoordinate)
        if (board[landingCoordinate] != null)
            throw LandingCoordinateIsTaken(landingCoordinate)
        landingCoordinate
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Pawn) return false
        return pieceColor == other.pieceColor
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}