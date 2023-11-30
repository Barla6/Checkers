package com.checkers

import com.checkers.checkers.errors.NoValidDirection
import com.checkers.checkers.Coordinate
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

internal class CoordinateBetweenTest {


    /**
     *
     */
    @Test
    fun coordinatesBetween_downRight() {
        val from = Coordinate(3, 4)
        val to = Coordinate(6, 7)

        val result = Coordinate.between(from, to)

        val expected = listOf(
            Coordinate(4, 5),
            Coordinate(5, 6)
        )

        assertSuccess(result)
        assertEqualListsIgnoreOrder(expected, result.getOrThrow())
    }

    @Test
    fun coordinatesBetween_downLeft() {
        val from = Coordinate(3, 4)
        val to = Coordinate(5, 2)

        val result = Coordinate.between(from, to)

        val expected = listOf(
            Coordinate(4, 3),
        )

        assertSuccess(result)
        assertEqualListsIgnoreOrder(expected, result.getOrThrow())
    }

    @Test
    fun coordinatesBetween_upRight() {
        val from = Coordinate(3, 4)
        val to = Coordinate(0, 7)

        val result = Coordinate.between(from, to)

        val expected = listOf(
            Coordinate(2, 5),
            Coordinate(1, 6),
        )

        assertSuccess(result)
        assertEqualListsIgnoreOrder(expected, result.getOrThrow())
    }

    @Test
    fun coordinatesBetween_upLeft() {
        val from = Coordinate(3, 4)
        val to = Coordinate(1, 2)

        val result = Coordinate.between(from, to)

        val expected = listOf(
            Coordinate(2, 3),
        )

        assertSuccess(result)
        assertEqualListsIgnoreOrder(expected, result.getOrThrow())
    }

    @Test
    fun coordinatesBetween_sameCoordinate() {
        val from = Coordinate(3, 4)

        val result = Coordinate.between(from, from)

        val expected = emptyList<Coordinate>()
        assertSuccess(result)
        assertEqualListsIgnoreOrder(expected, result.getOrThrow())
    }

    @Test
    fun coordinatesBetween_emptyRange() {
        val from = Coordinate(3, 4)
        val to = Coordinate(4, 5)

        val result = Coordinate.between(from, to)

        val expected = emptyList<Coordinate>()

        assertSuccess(result)
        assertEqualListsIgnoreOrder(expected, result.getOrThrow())
    }

    @Test
    fun coordinatesBetween_noValidDirection() {

        assertThrows<NoValidDirection> {
            val from = Coordinate(3, 4)
            val to = Coordinate(5, 5)

            val result = Coordinate.between(from, to)

            assertFailure(result)

            result.getOrThrow()
        }
    }
}