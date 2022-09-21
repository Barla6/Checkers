package com.models

import com.checkers.models.Board

typealias BoardToSend = MutableList<SquareToSend>

fun createBoardToSend(board: Board): BoardToSend {
    return board.board.mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, piece ->
            SquareToSend(rowIndex, colIndex, piece)
        }
    }.flatten().toMutableList()
}