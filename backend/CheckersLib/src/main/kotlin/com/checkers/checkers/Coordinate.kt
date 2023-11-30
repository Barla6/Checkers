package com.checkers.checkers

import com.checkers.checkers.errors.CoordinateOutOfBoard
import com.checkers.utlis.rangeBetween

data class Coordinate(val row: Int, val col: Int) : Cloneable {

    public override fun clone() = Coordinate(row, col)

    companion object {

        fun tryNew(row: Int, col: Int): Result<Coordinate> = runCatching {
            if (row < 0 || row > 7 || col < 0 || col > 7)
                throw CoordinateOutOfBoard("can't create coordinate ($row, $col) - not valid")
            return@runCatching Coordinate(row, col)
        }

        fun between(from: Coordinate, to: Coordinate): Result<List<Coordinate>> = runCatching {
            if (from == to) return@runCatching emptyList<Coordinate>()

            Direction.get(from, to).onFailure { throw it }

            val rowRange = rangeBetween(from.row, to.row)
            val colRange = rangeBetween(from.col, to.col)

            rowRange.zip(colRange).toList().map { Coordinate(it.first, it.second) }
        }
    }

    fun step(direction: Direction, numberOfSteps: Int = 1): Result<Coordinate> = runCatching {

        var newCoordinate = this
        (1..numberOfSteps).forEach { _ ->
            newCoordinate = (newCoordinate + direction).getOrThrow()
        }
        return@runCatching newCoordinate
    }

    operator fun plus(direction: Direction): Result<Coordinate> = runCatching {
        return tryNew(
            this.row + direction.rowDirection,
            this.col + direction.colDirection
        )
    }

    fun coordinatesInDirection(direction: Direction): List<Coordinate> {
        return generateSequence(this.step(direction).getOrNull()) { lastCord ->
            lastCord.step(direction).getOrNull()
        }.toList()

    }

    override fun toString(): String {
        return "($row, $col)"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Coordinate) return false
        return col == other.col && row == other.row
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        return result
    }
}