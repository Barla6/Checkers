package com.checkers

import com.checkers.models.*
import java.util.*

object GameRunner {

    suspend fun runGame(game: Game) {
        checkGameType(game).runGame(game)
//        game.printGameDetails()
    }

    private fun checkGameType(game: Game): GameType {
        return when (game.player1) {
            is AIPlayer -> when (game.player2) {
                is AIPlayer -> GameType.COMPUTER_VS_COMPUTER
                else -> GameType.HUMAN_VS_COMPUTER
            }
            else -> when (game.player2) {
                is AIPlayer -> GameType.HUMAN_VS_COMPUTER
                else -> GameType.HUMAN_VS_HUMAN
            }
        }
    }
}

enum class GameType {
    HUMAN_VS_HUMAN {
        override suspend fun runGame(game: Game) {
            TODO("Not yet implemented")
        }
    },
    HUMAN_VS_COMPUTER {
        override suspend fun runGame(game: Game) {
            val reader = Scanner(System.`in`)
            var currentPlayer = game.getRandomPlayer()
            var oppositePlayer = game.getOppositePlayer(currentPlayer)!!
            while (!game.isOver) {

                if (currentPlayer is HumanPlayer) {
                    val movesTree = MovesTree.create(currentPlayer, oppositePlayer, game.board, 1)
                    if (movesTree.nextSteps!!.isEmpty())
                        game.winner = oppositePlayer
                    val leadingStepsAndFinalBoards = movesTree.getLeadingStepsAndFinalBoards()

                    println("choose move:")
                    leadingStepsAndFinalBoards.map { it.leadingStep }.forEachIndexed { index, step ->
                        println("${index+1}) $step")
                    }
                    val chosenStepIndex = reader.nextInt()-1
                    val stepSequence = leadingStepsAndFinalBoards[chosenStepIndex].leadingStep

                    game.board = stepSequence.resultBoard
                }
                if (currentPlayer is AIPlayer) {
                    val turnResult = currentPlayer.playTurn(game)
                    if (turnResult == null) {
                        game.winner = oppositePlayer
                        return
                    }

                    game.board = turnResult
                }

                println(game.board)
                game.turnCounter++

                game.checkForWinner()
                if (game.isOver) return

                val playerHolder = currentPlayer
                currentPlayer = oppositePlayer
                oppositePlayer = playerHolder
            }
        }
    },
    COMPUTER_VS_COMPUTER {
        override suspend fun runGame(game: Game) {
            var currentPlayer = game.getRandomPlayer() as AIPlayer
            var oppositePlayer = game.getOppositePlayer(currentPlayer)!!
            while (!game.isOver) {
                val newBoard = currentPlayer.playTurn(game)
                game.turnCounter++
                if (newBoard != null) {
                    game.board = newBoard
                    if (game.board.countPiecesOfPlayer(oppositePlayer) == 0) game.winner = currentPlayer
//                    game.board.print()
                } else {
                    game.winner = currentPlayer
                }
                val playerHolder = currentPlayer
                currentPlayer = oppositePlayer as AIPlayer
                oppositePlayer = playerHolder
            }
        }
    };

    abstract suspend fun runGame(game: Game)
}