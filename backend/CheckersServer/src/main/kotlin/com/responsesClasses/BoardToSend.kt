package com.responsesClasses

import com.checkers.models.Board
import com.checkers.models.Piece
import com.checkers.models.PieceType
import com.management.PlayersManager

typealias BoardToSend = List<List<PieceToSend?>>

fun createBoardToSend(board: Board): BoardToSend {
    return board.board.map { row ->
        row.map { piece ->
                if (piece == null) null
                else PieceToSend(piece.player.id.toString(), piece.type.name)
        }.toList()
    }.toList()
}

fun createBoard(boardToSend: BoardToSend): Board {
    return Board(boardToSend.map { row ->
        row.map { piece ->
            if (piece == null) null
            else {
                Piece(
                    PlayersManager.getPlayer(piece.playerId)!!,
                    if (piece.type == PieceType.REGULAR.name) PieceType.REGULAR else PieceType.KING
                )
            }
        }.toTypedArray()
    }.toTypedArray())
}