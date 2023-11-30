package com.checkers

import com.checkers.checkers.Board
import com.checkers.checkers.Coordinate
import com.checkers.checkers.PieceColor
import com.checkers.checkers.Step
import com.checkers.checkers.piece.King
import com.checkers.checkers.piece.Pawn
import kotlin.test.Test

internal class PossibleImmediateStepsTest {

    private val myPawn = Pawn(PieceColor.WHITE)
    private val myKing = King(PieceColor.WHITE)

    private val enemyPiece = Pawn(PieceColor.BLACK)
    private val friendlyPiece = Pawn(PieceColor.WHITE)

    /**
     * check for possible immediate steps for pawn - simple and eating
     * EXPECTATION: list of 2 steps - simple and eating
     */
    @Test
    fun possibleImmediateSteps_pawn_hasNotEaten() {
        val board = Board()

        val myCoordinate = Coordinate(2, 3)

        board.placePiece(myPawn, myCoordinate)
        board.placePiece(enemyPiece, Coordinate(3, 2))

        val result = myPawn.possibleImmediateSteps(board, myCoordinate, false)

        val expected = listOf(
            Step(
                myCoordinate,
                Coordinate(3, 4),
                false,
                board.clone().apply { executeStep(myCoordinate, Coordinate(3, 4)) }
            ),
            Step(
                myCoordinate,
                Coordinate(4, 1),
                true,
                board.clone().apply { executeStep(myCoordinate, Coordinate(4, 1)) }
            )
        )

        assertEqualListsIgnoreOrder(expected, result)

    }

    /**
     * check for possible immediate steps for pawn - simple and eating
     * EXPECTATION: list of 1 step - eating
     */
    @Test
    fun possibleImmediateSteps_pawn_hasEaten() {
        val board = Board()

        val myCoordinate = Coordinate(2, 3)

        board.placePiece(myPawn, myCoordinate)
        board.placePiece(enemyPiece, Coordinate(3, 2))

        val result = myPawn.possibleImmediateSteps(board, myCoordinate, true)

        val expected = listOf(
            Step(
                myCoordinate,
                Coordinate(4, 1),
                true,
                board.clone().apply { executeStep(myCoordinate, Coordinate(4, 1)) }
            )
        )

        assertEqualListsIgnoreOrder(expected, result)

    }

    /**
     * check for possible immediate steps for pawn when there are no possible steps
     * EXPECTATION: empty list
     */
    @Test
    fun possibleImmediateSteps_pawn_noPossibleSteps() {
        val board = Board()

        val myCoordinate = Coordinate(2, 3)

        board.placePiece(myPawn, myCoordinate)
        board.placePiece(friendlyPiece, Coordinate(3, 2))
        board.placePiece(friendlyPiece, Coordinate(3, 4))

        val result = myPawn.possibleImmediateSteps(board, myCoordinate, false)

        val expected = emptyList<Step>()

        assertEqualListsIgnoreOrder(expected, result)
    }

    /**
     * check for possible immediate steps for king - simple and eating
     * EXPECTATION: list of steps - simple and eating
     */
    @Test
    fun possibleImmediateSteps_king_hasNotEaten() {
        val board = Board()

        val myCoordinate = Coordinate(2, 3)

        board.placePiece(myKing, myCoordinate)
        board.placePiece(enemyPiece, Coordinate(3, 2))
        board.placePiece(friendlyPiece, Coordinate(1, 2))
        board.placePiece(friendlyPiece, Coordinate(1, 4))

        val result = myKing.possibleImmediateSteps(board, myCoordinate, false)

        val expected = listOf(
            Step(
                myCoordinate,
                Coordinate(3, 4),
                false,
                board.clone().apply { executeStep(myCoordinate, Coordinate(3, 4)) }
            ),
            Step(
                myCoordinate,
                Coordinate(4, 5),
                false,
                board.clone().apply { executeStep(myCoordinate, Coordinate(4, 5)) }
            ),
            Step(
                myCoordinate,
                Coordinate(5, 6),
                false,
                board.clone().apply { executeStep(myCoordinate, Coordinate(5, 6)) }
            ),
            Step(
                myCoordinate,
                Coordinate(6, 7),
                false,
                board.clone().apply { executeStep(myCoordinate, Coordinate(6, 7)) }
            ),
            Step(
                myCoordinate,
                Coordinate(4, 1),
                true,
                board.clone().apply { executeStep(myCoordinate, Coordinate(4, 1)) }
            ),
            Step(
                myCoordinate,
                Coordinate(5, 0),
                true,
                board.clone().apply { executeStep(myCoordinate, Coordinate(5, 0)) }
            )
        )

        assertEqualListsIgnoreOrder(expected, result)

    }

    /**
     * check for possible immediate steps for king - only eating
     * EXPECTATION: list of steps - only eating
     */
    @Test
    fun possibleImmediateSteps_king_hasEaten() {
        val board = Board()

        val myCoordinate = Coordinate(2, 3)

        board.placePiece(myKing, myCoordinate)
        board.placePiece(enemyPiece, Coordinate(3, 2))
        board.placePiece(friendlyPiece, Coordinate(1, 2))
        board.placePiece(friendlyPiece, Coordinate(1, 4))

        val result = myKing.possibleImmediateSteps(board, myCoordinate, true)

        val expected = listOf(
            Step(
                myCoordinate,
                Coordinate(4, 1),
                true,
                board.clone().apply { executeStep(myCoordinate, Coordinate(4, 1)) }
            ),
            Step(
                myCoordinate,
                Coordinate(5, 0),
                true,
                board.clone().apply { executeStep(myCoordinate, Coordinate(5, 0)) }
            )
        )

        assertEqualListsIgnoreOrder(expected, result)

    }

    /**
     * check for possible immediate steps for king - only eating
     * EXPECTATION: empty list
     */
    @Test
    fun possibleImmediateSteps_king_noPossibleMoves() {
        val board = Board()

        val myCoordinate = Coordinate(2, 3)

        board.placePiece(myKing, myCoordinate)
        board.placePiece(friendlyPiece, Coordinate(3, 2))
        board.placePiece(friendlyPiece, Coordinate(1, 2))
        board.placePiece(friendlyPiece, Coordinate(1, 4))
        board.placePiece(friendlyPiece, Coordinate(3, 4))

        val result = myKing.possibleImmediateSteps(board, myCoordinate, false)

        val expected = emptyList<Step>()

        assertEqualListsIgnoreOrder(expected, result)
    }
}