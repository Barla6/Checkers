package com.checkers.checkers

import com.checkers.checkers.errors.LandingCoordinateIsTaken
import com.checkers.checkers.errors.NoPieceFound
import com.checkers.checkers.piece.Pawn
import com.checkers.checkers.piece.Piece

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
            .map { coordinate -> coordinate to this[coordinate]!!}
            .flatMap { (coordinate, piece) -> piece.possibleMoves(this, coordinate) }
    }

    fun immediatePossibleSteps(startingCoordinate: Coordinate, hasEaten: Boolean): Result<List<Step>> = runCatching {
        val piece = this[startingCoordinate]
            ?: throw NoPieceFound("Piece was expected in $startingCoordinate to find immediate possible moves")
        piece.possibleImmediateSteps(this, startingCoordinate, hasEaten)
    }

    fun executeStep(from: Coordinate, to: Coordinate): Result<Unit> = runCatching {
        movePiece(from, to)
        Coordinate.between(from, to)
            .onSuccess { it.forEach { coordinate -> removePiece(coordinate) } }
            .onFailure { throw it }
    }

    fun executeTurn(turn: Turn) {
        turn.trail.zipWithNext { from, to -> executeStep(from, to) }
    }

    private fun movePiece(from: Coordinate, to: Coordinate) {
        var piece = removePiece(from) ?:

            throw NoPieceFound("Couldn't move piece - piece doesn't exist at $from")
        if (this[to] != null)
            throw LandingCoordinateIsTaken(to)

        if (piece is Pawn && piece crowingRowIs to.row) {
            piece = piece.toKing()
        }
        placePiece(piece, to)
    }

    private fun removePiece(coordinate: Coordinate): Piece? {
        val removedPiece = this[coordinate]
        placePiece(null, coordinate)
        return removedPiece
    }

    internal fun placePiece(piece: Piece?, coordinate: Coordinate) {
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
        return tiles.map { row ->
            row.mapNotNull { tile ->
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

    override fun equals(other: Any?): Boolean {
        if (other !is Board) return false
        return this.tiles.zip(other.tiles).all { (row, otherRow) ->
            row.zip(otherRow).all { (tile,otherTile) ->
                tile == otherTile
            }
        }
    }

    override fun hashCode(): Int {
        return tiles.hashCode()
    }
}
