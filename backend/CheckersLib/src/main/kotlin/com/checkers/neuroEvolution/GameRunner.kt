package com.checkers.neuroEvolution

import com.checkers.models.Game
import com.checkers.models.PieceColor

class GameRunner(private val game: Game, player1: AIPlayer, player2: AIPlayer) {

    private lateinit var currentPlayer: Pair<PieceColor, AIPlayer>
    private var turnCounter = 0
    private var winnerColor: PieceColor? = null
    private val players = mapOf(PieceColor.WHITE to player1, PieceColor.BLACK to player2)

    companion object {
        private const val MAX_TURNS = 60
    }

    suspend fun run() {

        currentPlayer = randomPlayer()
        val (color, player) = currentPlayer

        while (turnCounter < MAX_TURNS || winnerColor != null) {
            val move = player.chooseMove(game, color)

            winnerColor =
                if (move != null) {
                    game.executeTurn(move)
                    turnCounter++
                    game.winner
                } else {
                    oppositePlayer().color
                }

            switchPlayer()
        }
    }

    fun updateFitness() {
        val whitePlayer = players[PieceColor.WHITE]!!
        val blackPlayer = players[PieceColor.BLACK]!!
        val whitePiecesLeft by lazy { game.board.countPiecesOfColor(PieceColor.WHITE) }
        val blackPiecesLeft by lazy { game.board.countPiecesOfColor(PieceColor.BLACK) }

        when (winnerColor) {
            PieceColor.WHITE -> whitePlayer.updateFitness { plus(whitePiecesLeft) }
            PieceColor.BLACK -> blackPlayer.updateFitness { plus(blackPiecesLeft) }
            else -> {
                whitePlayer.updateFitness {
                    minus(blackPiecesLeft / 12).plus(whitePiecesLeft)
                }
                blackPlayer.updateFitness {
                    minus(whitePiecesLeft / 12).plus(blackPiecesLeft)
                }
            }
        }
    }

    private fun randomPlayer(): Pair<PieceColor, AIPlayer> {
        val color = PieceColor.random()
        return color to players[color]!!
    }

    private fun oppositePlayer(): Pair<PieceColor, AIPlayer> {
        val (color, _) = currentPlayer
        val enemyColor = color.enemy
        return enemyColor to players[enemyColor]!!
    }

    private fun switchPlayer() {
        currentPlayer = oppositePlayer()
    }
}

val Pair<PieceColor, AIPlayer>.color
    get() = first
