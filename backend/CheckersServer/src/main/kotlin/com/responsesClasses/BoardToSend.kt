package com.responsesClasses

import com.checkers.models.Board

typealias BoardToSend = MutableList<MutableList<PieceToSend?>>

fun createBoardToSend(board: Board): BoardToSend {
    return board.board.map { row ->
        row.map { piece ->
                if (piece == null) null
                else PieceToSend(piece.player.id.toString(), piece.type.name)

        }.toMutableList()
    }.toMutableList()
}