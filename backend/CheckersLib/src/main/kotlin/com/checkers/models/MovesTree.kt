package com.checkers.models

import com.checkers.utlis.asyncMap
import com.checkers.utlis.initOnce
import kotlinx.coroutines.*

data class MovesTree(val stepSequence: StepSequence? = null) {
    private val scope = CoroutineScope(Dispatchers.Default)
    var nextSteps: List<MovesTree>? by initOnce()

    companion object {
        suspend fun create(startPlayer: Player, oppositePlayer: Player, board: Board, depth: Int = 1): MovesTree =
            MovesTree(null).apply {
                nextSteps = getAllPossibleNextMovesAsync(startPlayer, oppositePlayer, board, depth)
            }
    }

    private suspend fun getAllPossibleNextMovesAsync(currentPlayer: Player, oppositePlayer: Player, board: Board, depth: Int): List<MovesTree>? {
        if (depth == 0 ||
            board.countPiecesOfPlayer(currentPlayer) == 0 ||
            board.countPiecesOfPlayer(oppositePlayer) == 0
        ) return null

        return board.getCoordinatesOfPlayer(currentPlayer)
            .map { StepSequence(board.clone(), listOf(it)) }
            .flatMap { it.getPossibleTurnsForPieceAsync() }
            .map { MovesTree(it) }
            .asyncMap(scope) { it.apply {
                nextSteps =
                    getAllPossibleNextMovesAsync(
                        oppositePlayer,
                        currentPlayer,
                        it.stepSequence!!.resultBoard,
                        depth - 1
                    )
            }
        }
    }

    /**
     * returns pairs of first leading stepSequence and final board
     */
    fun getLeadingStepsAndFinalBoards(): List<LeadingStepAndFinalBoard> {
        return nextSteps
            ?.map { movesTree ->
                movesTree.getFinalBoards()
                    .map { Pair(movesTree.stepSequence!!, it) }
            }
            ?.flatten()
            ?: listOf()
    }

    private fun getFinalBoards(): List<Board> =
        nextSteps?.map { it.getFinalBoards() }?.flatten()
            ?: if (stepSequence?.resultBoard == null) listOf()
            else listOf(stepSequence.resultBoard)
}

