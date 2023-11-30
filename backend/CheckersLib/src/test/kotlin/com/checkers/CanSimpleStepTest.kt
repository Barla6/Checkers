package com.checkers

import com.checkers.checkers.errors.CoordinateOutOfBoard
import com.checkers.checkers.errors.LandingCoordinateIsTaken
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

internal class CanSimpleStepTest {

    private val myPawn = Pawn(PieceColor.WHITE)
    private val myKing = King(PieceColor.WHITE)

    private val enemyPiece = Pawn(PieceColor.BLACK)
    private val friendlyPiece = Pawn(PieceColor.WHITE)

    /**
     * check if pawn can step to empty place
     * EXPECTATION: success
     */
    @Test
    fun canSimpleStep_pawn() {
        val board = Board()

        val myCoordinate = Coordinate(2, 3)
        val direction = Direction.DOWN_RIGHT

        board.placePiece(myPawn, myCoordinate)

        val result = myPawn.canSimpleStep(myCoordinate, direction, board)

        val expected = Coordinate(3, 4)

        assertSuccess(result)
        assertEquals(expected, result.getOrThrow())
    }

    /**
     * check if pawn can step to place taken by enemy piece.
     * EXPECTATION: failure
     */
    @Test
    fun canSimpleStep_pawn_targetIsTakenByEnemy() {

        assertThrows<LandingCoordinateIsTaken> {
            val board = Board()

            val myCoordinate = Coordinate(2, 3)
            val nextCoordinate = Coordinate(3, 4)
            val direction = Direction.DOWN_RIGHT

            board.placePiece(myPawn, myCoordinate)
            board.placePiece(enemyPiece, nextCoordinate)

            val result = myPawn.canSimpleStep(myCoordinate, direction, board)

            assertFailure(result)

            result.getOrThrow()
        }
    }

    /**
     * check if pawn can step to place taken by friendly piece.
     * EXPECTATION: failure
     */
    @Test
    fun canSimpleStep_pawn_targetIsTakenByFriend() {

        assertThrows<LandingCoordinateIsTaken> {

            val board = Board()

            val myCoordinate = Coordinate(2, 3)
            val nextCoordinate = Coordinate(3, 4)
            val direction = Direction.DOWN_RIGHT

            board.placePiece(myPawn, myCoordinate)
            board.placePiece(friendlyPiece, nextCoordinate)

            val result = myPawn.canSimpleStep(myCoordinate, direction, board)

            assertFailure(result)

            result.getOrThrow()
        }
    }

    /**
     * check if pawn can step to place out of board
     * EXPECTATION: failure
     */
    @Test
    fun canSimpleStep_pawn_targetIsOutOfBoard() {

        assertThrows<CoordinateOutOfBoard> {
            val board = Board()

            val myCoordinate = Coordinate(6, 7)
            val direction = Direction.DOWN_RIGHT

            board.placePiece(myPawn, myCoordinate)

            val result = myPawn.canSimpleStep(myCoordinate, direction, board)

            assertFailure(result)

            result.getOrThrow()
        }
    }

    /**
     * check if king can step to empty place next to it.
     * EXPECTATION: yes
     */
    @Test
    fun canSimpleStep_king() {
        val board = Board()

        val myCoordinate = Coordinate(2, 3)
        val landingCoordinate = Coordinate(3, 4)

        board.placePiece(myKing, myCoordinate)

        val result = myKing.canSimpleStep(myCoordinate, landingCoordinate, board)

        assertTrue(result)
    }

    /**
     * check if king can step to empty place distant from it.
     * EXPECTATION: yes
     */
    @Test
    fun canSimpleStep_king_distantStep() {
        val board = Board()

        val myCoordinate = Coordinate(2, 3)
        val landingCoordinate = Coordinate(5, 6)

        board.placePiece(myKing, myCoordinate)

        val result = myKing.canSimpleStep(myCoordinate, landingCoordinate, board)

        assertTrue(result)
    }

    /**
     * check if king can step to place taken by enemy piece.
     * EXPECTATION: no
     */
    @Test
    fun canSimpleStep_king_targetIsTakenByEnemy() {
        val board = Board()

        val myCoordinate = Coordinate(2, 3)
        val landingCoordinate = Coordinate(5, 6)

        board.placePiece(myKing, myCoordinate)
        board.placePiece(enemyPiece, landingCoordinate)

        val result = myKing.canSimpleStep(myCoordinate, landingCoordinate, board)

        assertFalse(result)
    }

    /**
     * check if king can step to place taken by friendly piece.
     * EXPECTATION: no
     */
    @Test
    fun canSimpleStep_king_targetIsTakenByFriend() {
        val board = Board()

        val myCoordinate = Coordinate(2, 3)
        val landingCoordinate = Coordinate(5, 6)

        board.placePiece(myKing, myCoordinate)
        board.placePiece(friendlyPiece, landingCoordinate)

        val result = myKing.canSimpleStep(myCoordinate, landingCoordinate, board)

        assertFalse(result)
    }

    /**
     * check if king can step to empty place when is enemy piece in the way.
     * EXPECTATION: no
     */
    @Test
    fun canSimpleStep_king_enemyPieceInRange() {
        val board = Board()

        val myCoordinate = Coordinate(2, 3)
        val nextCoordinate = Coordinate(3, 4)
        val landingCoordinate = Coordinate(5, 6)

        board.placePiece(myKing, myCoordinate)
        board.placePiece(enemyPiece, nextCoordinate)

        val result = myKing.canSimpleStep(myCoordinate, landingCoordinate, board)

        assertFalse(result)
    }

    /**
     * check if king can step to empty place when is friendly piece in the way.
     * EXPECTATION: no
     */
    @Test
    fun canSimpleStep_king_friendlyPieceInRange() {
        val board = Board()

        val myCoordinate = Coordinate(2, 3)
        val nextCoordinate = Coordinate(4, 5)
        val landingCoordinate = Coordinate(5, 6)

        board.placePiece(myKing, myCoordinate)
        board.placePiece(friendlyPiece, nextCoordinate)

        val result = myKing.canSimpleStep(myCoordinate, landingCoordinate, board)

        assertFalse(result)
    }
}