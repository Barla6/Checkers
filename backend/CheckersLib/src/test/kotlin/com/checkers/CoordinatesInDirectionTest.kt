package com.checkers

import com.checkers.checkers.Coordinate
import com.checkers.checkers.Direction
import kotlin.test.Test

internal class CoordinatesInDirectionTest {

    @Test
    fun coordinatesInDirection_regular() {
        val myCoordinate = Coordinate(3, 3)
        val direction = Direction.DOWN_RIGHT

        val result = myCoordinate.coordinatesInDirection(direction)

        val expected = listOf(
            Coordinate(4, 4),
            Coordinate(5, 5),
            Coordinate(6, 6),
            Coordinate(7, 7)
        )

        assertEqualListsIgnoreOrder(expected, result)
    }

    @Test
    fun coordinatesInDirection_rowOutOfBoard() {
        val myCoordinate = Coordinate(6, 3)
        val direction = Direction.DOWN_RIGHT

        val result = myCoordinate.coordinatesInDirection(direction)

        val expected = listOf(
            Coordinate(7, 4)
        )

        assertEqualListsIgnoreOrder(expected, result)
    }

    @Test
    fun coordinatesInDirection_colOutOfBoard() {
        val myCoordinate = Coordinate(3, 5)
        val direction = Direction.DOWN_RIGHT

        val result = myCoordinate.coordinatesInDirection(direction)

        val expected = listOf(
            Coordinate(4, 6),
            Coordinate(5, 7)
        )

        assertEqualListsIgnoreOrder(expected, result)

    }

    @Test
    fun coordinatesInDirection_lastCoordinateInDirection() {
        val myCoordinate = Coordinate(7, 7)
        val direction = Direction.DOWN_RIGHT

        val result = myCoordinate.coordinatesInDirection(direction)

        val expected = emptyList<Coordinate>()

        assertEqualListsIgnoreOrder(expected, result)
    }
}