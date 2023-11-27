package com.checkers

import com.checkers.models.Game
import com.checkers.neuroEvolution.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

//    val aiPlayer = AIPlayer(1, Level.HARD)
//
//    val game = Game()
//
//    val stepSequence = aiPlayer.chooseMove(game, PieceColor.WHITE)
//
//    println(game.board.toString())
//    println("_________________________________")
//    if (stepSequence != null) {
//        game.playStep(stepSequence)
//        println(game.board.toString())
//    }

//    Evolution().draw()

//    val a = AIPlayer(1, NeuralNetwork.random(1, 16, 32))
//
//    println(a.brain.fitness)
//
//    a.updateFitness {
//        plus(10).minus(2)
//    }
//
//    println(a.brain.fitness)

//    Evolution(10, 5).draw()

    val g = GameRunner(Game(), AIPlayer(1, Level.MEDIUM), AIPlayer(2, Level.MEDIUM))
    g.run()
}