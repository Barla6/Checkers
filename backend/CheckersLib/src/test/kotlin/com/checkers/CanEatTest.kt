package com.checkers

import com.checkers.checkers.errors.CoordinateOutOfBoard
import com.checkers.checkers.errors.LandingCoordinateIsTaken
import com.checkers.checkers.errors.NoPieceToEat
import com.checkers.checkers.Board
import com.checkers.checkers.Coordinate
import com.checkers.checkers.Direction
import com.checkers.checkers.PieceColor
import com.checkers.checkers.piece.King
import com.checkers.checkers.piece.Pawn
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class CanEatTest {

    private val myPawn = Pawn(PieceColor.WHITE)
    private val myKing = King(PieceColor.WHITE)

    private val enemyPiece = Pawn(PieceColor.BLACK)
    private val friendlyPiece = Pawn(PieceColor.WHITE)

    /**
     * check if pawn can eat enemy piece next to it.
     * EXPECTATION: success
     */
    @Test
    fun canEat_pawn() {
        val board = Board()

        val myCoordinate = Coordinate(1, 2)
        val nextCoordinate = Coordinate(2, 3)
        val direction = Direction.DOWN_RIGHT

        board.placePiece(myPawn, myCoordinate)
        board.placePiece(enemyPiece, nextCoordinate)

        val result = myPawn.canEat(myCoordinate, direction, board)

        val expected = Coordinate(3, 4)

        assertSuccess(result)
        assertEquals(expected, result.getOrThrow())
    }

    /**
     * check if pawn can eat when there is no other piece.
     * EXPECTATION: failure
     */
    @Test
    fun canEat_pawn_nothingToEat() {
        assertThrows<NoPieceToEat> {
            val board = Board()

            val myCoordinate = Coordinate(1, 2)
            val direction = Direction.DOWN_RIGHT

            board.placePiece(myPawn, myCoordinate)

            val result = myPawn.canEat(myCoordinate, direction, board)

            assertFailure(result)

            result.getOrThrow()
        }
    }

    /**
     * check if pawn can eat friendly piece next to it.
     * EXPECTATION: failure
     */
    @Test
    fun canEat_pawn_friendlyPieceToEat() {

        assertThrows<NoPieceToEat> {
            val board = Board()

            val myCoordinate = Coordinate(1, 2)
            val nextCoordinate = Coordinate(2, 3)

            val direction = Direction.DOWN_RIGHT

            board.placePiece(myPawn, myCoordinate)
            board.placePiece(friendlyPiece, nextCoordinate)

            val result = myPawn.canEat(myCoordinate, direction, board)

            assertFailure(result)

            result.getOrThrow()
        }
    }

    /**
     * check if pawn can eat enemy piece next to it, when the landing place is taken.
     * EXPECTATION: failure
     */
    @Test
    fun canEat_pawn_landingPlaceTaken() {

        assertThrows<LandingCoordinateIsTaken> {
            val board = Board()

            val myCoordinate = Coordinate(1, 2)
            val nextCoordinate = Coordinate(2, 3)
            val landingCoordinate = Coordinate(3, 4)

            val direction = Direction.DOWN_RIGHT

            board.placePiece(myPawn, myCoordinate)
            board.placePiece(enemyPiece, nextCoordinate)
            board.placePiece(friendlyPiece, landingCoordinate)

            val result = myPawn.canEat(myCoordinate, direction, board)

            assertFailure(result)

            result.getOrThrow()
        }

    }

    /**
     * check if pawn can eat enemy piece next to it, when the landing place is out of board
     * EXPECTATION: failure
     */
    @Test
    fun canEat_pawn_nextPlaceOutOfBoard() {

        assertThrows<CoordinateOutOfBoard> {

            val board = Board()

            val myCoordinate = Coordinate(6, 7)
            val direction = Direction.DOWN_RIGHT

            board.placePiece(myPawn, myCoordinate)

            val result = myPawn.canEat(myCoordinate, direction, board)

            assertFailure(result)

            result.getOrThrow()

        }
    }

    /**
     * check if pawn can eat enemy piece next to it, when the landing place is out of board
     * EXPECTATION: failure
     */
    @Test
    fun canEat_pawn_landingPlaceOutOfBoard() {

        assertThrows<CoordinateOutOfBoard> {

            val board = Board()

            val myCoordinate = Coordinate(5, 6)
            val nextCoordinate = Coordinate(6, 7)
            val direction = Direction.DOWN_RIGHT

            board.placePiece(myPawn, myCoordinate)
            board.placePiece(enemyPiece, nextCoordinate)

            val result = myPawn.canEat(myCoordinate, direction, board)

            assertFailure(result)

            result.getOrThrow()
        }
    }

    /**
     * check if king can eat enemy piece next to it.
     * EXPECTATION: yes
     */
    @Test
    fun canEat_king_simpleEating() {
        val board = Board()

        val myCoordinate = Coordinate(1, 2)
        val nextCoordinate = Coordinate(2, 3)
        val landingCoordinate = Coordinate(3, 4)

        board.placePiece(myKing, myCoordinate)
        board.placePiece(enemyPiece, nextCoordinate)

        val result = myKing.canEat(myCoordinate, landingCoordinate, board)

        assertTrue(result)
    }

    /**
     * check if king can eat enemy piece distant from it.
     * EXPECTATION: yes
     */
    @Test
    fun canEat_king_simpleEatingFromDistance() {
        val board = Board()

        val myCoordinate = Coordinate(1, 2)
        val nextCoordinate = Coordinate(4, 5)
        val landingCoordinate = Coordinate(5, 6)

        board.placePiece(myKing, myCoordinate)
        board.placePiece(enemyPiece, nextCoordinate)

        val result = myKing.canEat(myCoordinate, landingCoordinate, board)

        assertTrue(result)
    }

    /**
     * check if king can eat when there are no other pieces.
     * EXPECTATION: no
     */
    @Test
    fun canEat_king_nothingToEat() {
        val board = Board()

        val myCoordinate = Coordinate(1, 2)
        val landingCoordinate = Coordinate(5, 6)

        board.placePiece(myKing, myCoordinate)

        val result = myKing.canEat(myCoordinate, landingCoordinate, board)

        assertFalse(result)
    }

    /**
     * check if king can eat friendly piece.
     * EXPECTATION: no
     */
    @Test
    fun canEat_king_friendlyPieceToEat() {
        val board = Board()

        val myCoordinate = Coordinate(1, 2)
        val nextCoordinate = Coordinate(4, 5)
        val landingCoordinate = Coordinate(5, 6)

        board.placePiece(myKing, myCoordinate)
        board.placePiece(friendlyPiece, nextCoordinate)

        val result = myKing.canEat(myCoordinate, landingCoordinate, board)

        assertFalse(result)
    }

    /**
     * check if king can eat enemy piece when there is a piece in the way.
     * EXPECTATION: no
     */
    @Test
    fun canEat_king_piecesInTheWay() {
        val board = Board()

        val myCoordinate = Coordinate(1, 2)
        val landingCoordinate = Coordinate(5, 6)

        board.placePiece(myKing, myCoordinate)
        board.placePiece(enemyPiece, Coordinate(3, 4))
        board.placePiece(enemyPiece, Coordinate(4, 5))

        val result = myKing.canEat(myCoordinate, landingCoordinate, board)

        assertFalse(result)
    }

    /**
     * check if king can eat enemy piece when landing coordinate is taken.
     * EXPECTATION: no
     */
    @Test
    fun canEat_king_landingPlaceTaken() {
        val board = Board()

        val myCoordinate = Coordinate(1, 2)
        val nextCoordinate = Coordinate(4, 5)
        val landingCoordinate = Coordinate(5, 6)


        board.placePiece(myKing, myCoordinate)
        board.placePiece(enemyPiece, nextCoordinate)
        board.placePiece(friendlyPiece, landingCoordinate)

        val result = myKing.canEat(myCoordinate, landingCoordinate, board)

        assertFalse(result)
    }
}