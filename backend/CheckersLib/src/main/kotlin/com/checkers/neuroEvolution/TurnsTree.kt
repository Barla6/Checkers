package com.checkers.neuroEvolution

import com.checkers.models.Board
import com.checkers.models.PieceColor
import com.checkers.models.Turn
import com.checkers.utlis.asyncMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class TurnsTree(
    private val turnTaken: Turn? = null,
    private val nextMoves: List<TurnsTree>,
) {

    private val isRoot = turnTaken == null

    fun finalBoardsByMoves(): Map<Board, Turn> {
        if (nextMoves.isEmpty() && isRoot) return emptyMap()
        if (nextMoves.isEmpty()) return mapOf(turnTaken!!.boardAfterTurn to turnTaken)

        val finalBoardsByMoves = nextMoves.flatMap { it.finalBoardsByMoves().toList() }.toMap()
        if (!isRoot) finalBoardsByMoves.values.forEach { _ -> turnTaken!! }
        return finalBoardsByMoves
    }


    companion object {

        private val scope = CoroutineScope(Dispatchers.Default)

        suspend fun new(startPieceColor: PieceColor, board: Board, depth: Int): TurnsTree {
            if (depth <= 0) throw Exception("Depth of TurnsTree must be a positive number")
            return TurnsTree(
                null,
                getNextMoves(startPieceColor, board, depth),
            )
        }

        private suspend fun getNextMoves(currentPieceColor: PieceColor, board: Board, depth: Int): List<TurnsTree> {
            if (depth == 0 ||
                board.countPiecesOfColor(currentPieceColor) == 0 ||
                board.countPiecesOfColor(currentPieceColor.enemy) == 0
            ) return emptyList()

            return board.possibleTurns(currentPieceColor).asyncMap(scope) {
                TurnsTree(
                    it,
                    getNextMoves(
                        currentPieceColor.enemy,
                        it.boardAfterTurn,
                        depth - 1,
                    )
                )
            }
        }
    }
}