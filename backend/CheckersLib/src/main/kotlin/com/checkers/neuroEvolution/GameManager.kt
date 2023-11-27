package com.checkers.neuroEvolution

import com.checkers.models.Game
import kotlinx.coroutines.*
import com.checkers.utlis.ProgressBar

class GameManager(private val population: Population, generationNumber: Int) {

    private val scope = CoroutineScope(Dispatchers.Default)
    private val gameAmount = population.brains.size * (population.brains.size - 1)
    private val progressBar = ProgressBar("Gen $generationNumber", gameAmount)

    suspend fun runGamesParallel() {
        progressBar.start()
        population.brains.flatMap { brain1 ->
            population.brains.map { brain2 ->
                scope.async {
                    createAndRunGame(brain1, brain2)?.updateFitness()
                }
            }
        }.awaitAll()
    }

    private suspend fun createAndRunGame(brain1: NeuralNetwork, brain2: NeuralNetwork): GameRunner? {

        if (brain1 == brain2) return null

        val player1 = AIPlayer(1, brain1)
        val player2 = AIPlayer(2, brain2)

        val game = Game()
        val gameRunner = GameRunner(game, player1, player2)
        gameRunner.run()

        progressBar.step()

        return gameRunner
    }
}