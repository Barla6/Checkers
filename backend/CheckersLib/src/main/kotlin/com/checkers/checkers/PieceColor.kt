package com.checkers.checkers

enum class PieceColor(
    val startingRows: List<Int>,
    val crowningRow: Int,
    val defaultDirections: List<Direction>,
) {
    WHITE(
        startingRows = listOf(0, 1, 2),
        crowningRow = 7,
        defaultDirections = listOf(Direction.DOWN_LEFT, Direction.DOWN_RIGHT),
    ) {
        override val enemy: PieceColor
            get() = BLACK
    },
    BLACK(
        startingRows = listOf(5, 6, 7),
        crowningRow = 0,
        defaultDirections = listOf(Direction.UP_LEFT, Direction.UP_RIGHT)
    ) {
        override val enemy: PieceColor
            get() = WHITE
    };

    companion object {
        fun colorForRow(row: Int): PieceColor? {
            return values().find { pieceColor -> row in pieceColor.startingRows }
        }

        fun random(): PieceColor {
            return values().random()
        }
    }

    abstract val enemy: PieceColor
}