package com.checkers.models.piece

import com.checkers.models.*
import java.util.*

sealed class Piece(val pieceColor: PieceColor): Cloneable {
    public abstract override fun clone(): Piece

    abstract fun getDirections(): List<Direction>

    abstract fun possibleImmediateSteps(board: Board, from: Coordinate, hasEaten: Boolean): List<Step>

    fun possibleMoves(board: Board, from: Coordinate): List<Turn> {
        val completed = mutableListOf<Turn>()
        val inProgress = Stack<Turn>()
        possibleImmediateSteps(board, from, false)
            .map { Turn(listOf(it)) }
            .forEach { inProgress.push(it) }

        while (inProgress.isNotEmpty()) {
            val move = inProgress.pop()
            completed.add(move)

            if (move.hasEaten) {
                possibleImmediateSteps(move.boardAfterTurn, move.trail.last(), true)
                    .forEach { step -> inProgress.push(move + step) }
            }
        }
        return completed
    }

    infix fun enemyOf(piece: Piece) : Boolean =
        pieceColor != piece.pieceColor
}