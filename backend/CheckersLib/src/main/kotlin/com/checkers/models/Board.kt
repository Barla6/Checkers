package com.checkers.models

import com.checkers.errors.NoPieceFound
import com.checkers.models.piece.Pawn
import com.checkers.models.piece.Piece

class Board(val tiles: Tiles = createEmptyTiles()) : Cloneable {

    companion object {
        private fun createEmptyTiles(): Tiles {
            return List(8) { row ->
                List(8) { col ->
                    Tile(Coordinate(row, col))
                }
            }
        }
    }

    fun possibleTurns(pieceColor: PieceColor): List<Turn> {
        return getCoordinatesOfPieceColor(pieceColor)
            .flatMap { possibleTurns(it).getOrThrow() }
    }

    private fun possibleTurns(startingCoordinate: Coordinate): Result<List<Turn>> = runCatching {
        val piece = this[startingCoordinate]
            ?: throw NoPieceFound("Piece was expected in $startingCoordinate to find possible turns")
        piece.possibleMoves(this, startingCoordinate)
    }

    fun immediatePossibleSteps(startingCoordinate: Coordinate, hasEaten: Boolean): Result<List<Step>> = runCatching {
        val piece = this[startingCoordinate]
            ?: throw NoPieceFound("Piece was expected in $startingCoordinate to find immediate possible moves")
        piece.possibleImmediateSteps(this, startingCoordinate, hasEaten)
    }

    fun executeStep(from: Coordinate, to: Coordinate) {
        movePiece(from, to)
        Coordinate.between(from, to).forEach { removePiece(it) }
    }

    fun executeTurn(turn: Turn) {
        turn.trail.zipWithNext { from, to -> executeStep(from, to) }
    }

    private fun movePiece(from: Coordinate, to: Coordinate) {
        var pieceToPlace = removePiece(from) ?: throw Error("Couldn't move piece - piece doesn't exist at $from")
        if (this[to] != null) throw Error("Couldn't move piece to $to - place was taken")

        if (pieceToPlace is Pawn && to.row == pieceToPlace.pieceColor.crowningRow) {
            pieceToPlace = pieceToPlace.toKing()
        }
        placePiece(pieceToPlace, to)
    }

    private fun removePiece(coordinate: Coordinate): Piece? {
        val removedPiece = this[coordinate]
        placePiece(null, coordinate)
        return removedPiece
    }

    private fun placePiece(piece: Piece?, coordinate: Coordinate) {
        tiles[coordinate.row][coordinate.col].piece = piece
    }

    operator fun get(coordinate: Coordinate): Piece? {
        return tiles[coordinate.row][coordinate.col].piece
    }

    fun piecesInRange(range: List<Coordinate>): List<Piece> {
        return range.mapNotNull { this[it] }
    }

    private fun countOnBoard(condition: (tile: Tile) -> Boolean): Int =
        tiles.sumOf { row ->
            row.count { tile -> condition(tile) }
        }

    fun countPiecesOfColor(pieceColor: PieceColor): Int {
        return countOnBoard { tile -> tile.piece?.pieceColor == pieceColor }
    }

    private fun getCoordinatesOfPieceColor(pieceColor: PieceColor): List<Coordinate> {
        return tiles.mapIndexed { rowIndex, row ->
            row.mapIndexedNotNull { colIndex, tile ->
                if (tile.piece?.pieceColor == pieceColor) tile.coordinate else null
            }
        }.flatten()
    }

    override fun toString(): String {
        return tiles.joinToString("\n") { row ->
            "| " +
                    row.joinToString(" | ") { tile ->
                        tile.toString()
                    } +
                    " |"
        }
    }

    public override fun clone() = Board(tiles.clone())
}
