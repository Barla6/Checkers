package com.checkers.models

import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sign

class Coordinate (val row: Int, val col: Int) : Cloneable {

    public override fun clone() = Coordinate(row, col)

    companion object {

        fun newValid(row: Int, col: Int): Result<Coordinate> = runCatching {
            if (row < 0 || row > 7 || col < 0 || col > 7)
                throw Error("can't create coordinate ($row, $col) - not valid")
            return@runCatching Coordinate(row, col)
        }

        fun between(from: Coordinate, to: Coordinate): List<Coordinate> {
            if (from == to) return listOf()

            Direction.getDirection(from, to)
                ?: throw Exception("can't create range from $from to $to")

            val rowRange = (from.row until to.row)
            val colRange = (from.col until to.col)

            return rowRange.zip(colRange).drop(1).toList().map { Coordinate(it.first, it.second) }
        }
    }

    fun step(direction: Direction, numberOfSteps: Int = 1): Result<Coordinate> = runCatching {

        var newCoordinate = this
        (1..numberOfSteps).forEach { _ ->
            val stepResult = newCoordinate + direction
            if (stepResult.isFailure) throw stepResult.exceptionOrNull()!!
            else newCoordinate = stepResult.getOrNull()!!
        }
        return@runCatching newCoordinate
    }

    operator fun plus(direction: Direction): Result<Coordinate> = runCatching {
            return newValid(
                this.row + direction.rowDirection,
                this.col + direction.colDirection
            )
    }

    fun getAllCoordinatesInDirection(direction: Direction): List<Coordinate> {
        val endRow = if (direction.rowDirection.sign < 0) 0 else 7
        val endCol = if (direction.colDirection.sign < 0) 0 else 7

        val amountOfSteps = min(
            abs(endRow - this.row),
            abs(endCol - this.col)
        )

        val endCoordinates = this.step(direction, amountOfSteps).getOrNull()!!

        return between(this, endCoordinates)
    }

    override fun toString(): String {
        return "($col, $row)"
    }
}