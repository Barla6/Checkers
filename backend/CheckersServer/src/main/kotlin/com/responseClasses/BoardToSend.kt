package com.responseClasses

import com.checkers.models.Board
import com.checkers.models.Coordinates

typealias BoardToSend = MutableList<SquareToSend>

fun createBoardToSend(board: Board): BoardToSend {
    return board.board.mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, piece ->
            val pieceToSend =
                if (piece == null) null
                else PieceToSend(piece.player.id, piece.type.name)
            val coordinates = Coordinates(rowIndex, colIndex)
            SquareToSend(coordinates, pieceToSend)
        }
    }.flatten().toMutableList()
}