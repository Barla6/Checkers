package com.checkers.models

import com.checkers.neuroEvolution.NeuralNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.checkers.utlis.asyncMapIndexed
import com.checkers.utlis.initOnce

class AIPlayer() : Player() {
    private val scope = CoroutineScope(Dispatchers.Default)

    var brain: NeuralNetwork by initOnce()

    constructor(brain: NeuralNetwork) : this() {
        this.brain = brain
        name = brain.name
    }

    constructor(level: GameLevel, name: String) : this() {
        this.brain = NeuralNetwork.ofLevel(level)
            .apply { this.name = name }
        this.name = name
    }

    suspend fun playTurn(game: Game): Board? {
        val movesTree = MovesTree.create(this, game.getOppositePlayer(this)!!, game.board, 3)
        val leadingStepsAndFinalBoards = movesTree.getLeadingStepsAndFinalBoards()
        if (leadingStepsAndFinalBoards.isEmpty()) return null
        val bestBoard = pickBoardAsync(leadingStepsAndFinalBoards.map { it.finalBoard })

        return leadingStepsAndFinalBoards.findLast { it.finalBoard == bestBoard }!!.leadingStep.resultBoard
    }

    private suspend fun pickBoardAsync(boards: List<Board>): Board {
        val bestBoard = boards
            .asyncMapIndexed(scope) {index, board -> index to brain.rate(board, this@AIPlayer)}
            .maxByOrNull { it.second }
        return boards[bestBoard!!.first]
    }

    fun clone(): AIPlayer = AIPlayer(this.brain).apply {
        id = this@AIPlayer.id
        name = this@AIPlayer.name
    }
}
