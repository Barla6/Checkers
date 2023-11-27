package com.checkers.neuroEvolution

import com.checkers.models.*
import com.checkers.utlis.asyncMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AIPlayer internal constructor(val id: Int, val brain: NeuralNetwork) {

    private val scope = CoroutineScope(Dispatchers.Default)

    constructor(id: Int, level: Level) : this(id, NeuralNetwork.ofLevel(level))

    suspend fun chooseMove(game: Game, pieceColor: PieceColor): Turn? {
        val turnsTree = TurnsTree.new(pieceColor, game.board, 3)
        val finalBoardsByMoves = turnsTree.finalBoardsByMoves()
        if (finalBoardsByMoves.isEmpty()) return null
        return pickMoveAsync(finalBoardsByMoves, pieceColor)
    }

    private suspend fun pickMoveAsync(finalBoardsByMoves: Map<Board, Turn>, pieceColor: PieceColor): Turn {
        return finalBoardsByMoves.entries.asyncMap(scope) { (board, turn) ->
            brain.rate(board, pieceColor) to turn
        }.maxByOrNull { (rate, _) -> rate }!!.turn
    }

    fun updateFitness(actions: Double.() -> Double) {
        brain.fitness = brain.fitness.actions()
    }
}

val Pair<Double, Turn>.turn
    get() = second