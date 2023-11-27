package com.checkers.models

import kotlin.math.abs
import kotlin.math.sign

enum class Direction(private val direction: Pair<Int, Int>) {
    DOWN_RIGHT(Pair(1, 1)),
    DOWN_LEFT(Pair(1, -1)),
    UP_RIGHT(Pair(-1, 1)),
    UP_LEFT(Pair(-1, -1));

    val rowDirection: Int
        get() = this.direction.first

    val colDirection: Int
        get() = this.direction.second

    companion object {
        fun getDirection(from: Coordinate, to: Coordinate): Direction? {
            val rowDirection = to.row - from.row
            val colDirection = to.col - from.col

            if (abs(rowDirection) != abs(colDirection)) return null

            return values().find { direction ->
                direction.rowDirection == rowDirection.sign &&
                        direction.colDirection == colDirection.sign
            }
        }
    }
}