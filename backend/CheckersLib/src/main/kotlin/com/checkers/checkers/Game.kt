package com.checkers.checkers

import com.checkers.checkers.piece.Pawn

class Game {

    internal val board: Board = initialBoard.clone()

    companion object {
        private val initialBoard = Board().apply { initBoard(this) }

        private fun initBoard(board: Board) {
            board.tiles.onEachIndexed { rowIndex, row ->
                row.onEachIndexed { _, tile ->
                    if (tile.isBlack()) {
                        val pieceColor = PieceColor.colorForRow(rowIndex)
                        tile.piece = when (pieceColor) {
                            null -> null
                            else -> Pawn(pieceColor)
                        }
                    }
                }
            }
        }
    }

    fun executeTurn(turn: Turn) {
        board.executeTurn(turn)
    }

    val winner: PieceColor?
        get() = when {
            board.countPiecesOfColor(PieceColor.WHITE) == 0 -> PieceColor.BLACK
            board.countPiecesOfColor(PieceColor.BLACK) == 0 -> PieceColor.WHITE
            else -> null
        }

    fun printBoard() {
        println(board.toString())
    }
}