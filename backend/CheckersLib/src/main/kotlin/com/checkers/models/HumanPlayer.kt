package com.checkers.models

class HumanPlayer(name: String): Player() {

    init {
        this.name = name
    }

    fun playTurn(sequence: List<Coordinates>, board: Board) =
        sequence.zipWithNext()
            .fold(board.clone()) { currentBoard, (startCoordinates, endCoordinates) ->
                currentBoard.executeStep(startCoordinates, endCoordinates)
            }
}