package com.checkers.models

class Game(val player1: Player, val player2: Player) {

    var board: Board = Board()
    var turnCounter = 0
    var winner: Player? = null
    var level: GameLevel? = null

    val isOver: Boolean
        get() = winner != null || turnCounter >= MAX_TURNS


    companion object {
        const val MAX_TURNS = 120
    }

    init {
        if (player1 is AIPlayer) {
            player1.apply {
                playerDirection = PlayerDirection.DOWNWARDS
            }
            player2.apply {
                playerDirection = PlayerDirection.UPWARDS
            }
        } else {
            player1.apply {
                playerDirection = PlayerDirection.UPWARDS
            }
            player2.apply {
                playerDirection = PlayerDirection.DOWNWARDS
            }
        }

        board.initGameBoard(player1, player2)
    }

    fun getRandomPlayer() = listOf(player1, player2).random()

    fun getOppositePlayer(player: Player): Player? =
        when(player.id) {
            player1.id -> player2
            player2.id -> player1
            else -> null
        }

    fun checkForWinner() {
        winner =  when {
            board.countPiecesOfPlayer(player1) == 0 -> player2
            board.countPiecesOfPlayer(player2) == 0 -> player1
            else -> null
        }
    }

    fun printGameDetails() {
        if (isOver) {
            println("GAME OVER")
            println("players: ${player1.name} VS ${player2.name}")
            if (winner != null) println("WINNER: ${this.winner?.name}")
            else println("It's a TIE")
            println("turnsCount: ${this.turnCounter}")
        } else {
            println("game in progress...")
        }
    }

    fun playHumanPlayerTurn(sequence: List<Coordinates>, player: HumanPlayer) {
        if (player.id != player1.id && player.id != player2.id) throw Throwable()
        board = player.playTurn(sequence, board)
        checkForWinner()
    }

    suspend fun playAIPlayerTurn(player: AIPlayer) {
        if (player.id != player1.id && player.id != player2.id) throw Throwable()
        board = player.playTurn(this).also {
            if (it == null) {
                winner = this.getOppositePlayer(player)
            }
        } ?: return
        checkForWinner()
    }
}
