package com.checkers

import com.checkers.checkers.errors.CoordinateOutOfBoard
import com.checkers.checkers.Coordinate
import com.checkers.checkers.Direction
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

internal class StepTest {

    @Test
    fun step_downRight() {
        val from = Coordinate(3, 4)
        val direction = Direction.DOWN_RIGHT

        val result = from.step(direction, 2)

        val expected = Coordinate(5, 6)

        assertSuccess(result)
        assertEquals(expected, result.getOrThrow())
    }

    @Test
    fun step_downLeft() {
        val from = Coordinate(3, 4)
        val direction = Direction.DOWN_LEFT

        val result = from.step(direction)

        val expected = Coordinate(4, 3)

        assertSuccess(result)
        assertEquals(expected, result.getOrThrow())
    }

    @Test
    fun step_upRight() {
        val from = Coordinate(3, 4)
        val direction = Direction.UP_RIGHT

        val result = from.step(direction)

        val expected = Coordinate(2, 5)

        assertSuccess(result)
        assertEquals(expected, result.getOrThrow())
    }

    @Test
    fun step_upLeft() {
        val from = Coordinate(3, 4)
        val direction = Direction.UP_LEFT

        val result = from.step(direction, 2)

        val expected = Coordinate(1, 2)

        assertSuccess(result)
        assertEquals(expected, result.getOrThrow())
    }

    @Test
    fun step_rowOutOfBoard() {
        assertThrows<CoordinateOutOfBoard> {
            val from = Coordinate(6, 2)
            val direction = Direction.DOWN_RIGHT

            val result = from.step(direction, 3)

            assertFailure(result)

            result.getOrThrow()
        }
    }

    @Test
    fun step_colOutOfBoard() {
        assertThrows<CoordinateOutOfBoard> {
            val from = Coordinate(3, 6)
            val direction = Direction.DOWN_RIGHT

            val result = from.step(direction, 3)

            assertFailure(result)

            result.getOrThrow()
        }
    }

    @Test
    fun step_lastCoordinateInDirection() {
        assertThrows<CoordinateOutOfBoard> {
            val from = Coordinate(7, 7)
            val direction = Direction.DOWN_RIGHT

            val result = from.step(direction)

            assertFailure(result)

            result.getOrThrow()
        }
    }
}