package com.checkers

import com.checkers.models.Board
import com.checkers.models.Coordinate
import com.checkers.models.PieceColor
import com.checkers.models.piece.King
import com.checkers.models.piece.Pawn
import com.checkers.models.piece.Piece
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class CanSimpleStepTest {

    private val myPawn = Pawn(PieceColor.WHITE)
    private val myKing = King(PieceColor.WHITE)
    private val board = Mockito.spy(Board())

    private val enemyPiece = Pawn(PieceColor.BLACK)
    private val friendlyPiece = Pawn(PieceColor.WHITE)

    @BeforeEach
    fun init() {

    }

    /**
     * check if regular piece can step to empty place.
     * EXPECTATION: yes
     */
    @Test
    fun canSimpleStep_pawn_yes() {
        val nextCoordinate = Coordinate(3, 4)
        val myCoordinate = Coordinate(2, 3)

        Mockito.doReturn(null).`when`(board)[nextCoordinate]
        Mockito.doReturn(myPawn).`when`(board)[myCoordinate]

        val result = myPawn.canSimpleStep(nextCoordinate, board)

        assertTrue(result)
    }

    /**
     * check if regular piece can step to place taken by enemy piece.
     * EXPECTATION: no
     */
    @Test
    fun canSimpleStep_pawn_targetIsTakenByEnemy_no() {
        val nextCoordinate = Coordinate(3, 4)
        val myCoordinate = Coordinate(2, 3)

        Mockito.doReturn(enemyPiece).`when`(board)[nextCoordinate]
        Mockito.doReturn(myPawn).`when`(board)[myCoordinate]

        val result = myPawn.canSimpleStep(nextCoordinate, board)

        assertFalse(result)
    }

    /**
     * check if regular piece can step to place taken by friendly piece.
     * EXPECTATION: no
     */
    @Test
    fun canSimpleStep_pawn_targetIsTakenByFriend_no() {
        val nextCoordinate = Coordinate(3, 4)
        val myCoordinate = Coordinate(2, 3)

        Mockito.doReturn(friendlyPiece).`when`(board)[nextCoordinate]
        Mockito.doReturn(myPawn).`when`(board)[myCoordinate]

        val result = myPawn.canSimpleStep(nextCoordinate, board)

        assertFalse(result)
    }

    /**
     * check if king piece can step to empty place next to it.
     * EXPECTATION: yes
     */
    @Test
    fun canSimpleStep_king_yes() {
        val landingCoordinate = Coordinate(3, 4)
        val myCoordinate = Coordinate(2, 3)

        Mockito.doReturn(null).`when`(board)[landingCoordinate]
        Mockito.doReturn(myKing).`when`(board)[myCoordinate]

        val piecesInRange = listOf<Piece>()

        val result = myKing.canSimpleStep(landingCoordinate, board, piecesInRange)

        assertTrue(result)
    }

    /**
     * check if king piece can step to empty place distant from it.
     * EXPECTATION: yes
     */
    @Test
    fun canSimpleStep_king_distantStep_yes() {
        val landingCoordinate = Coordinate(5, 6)
        val myCoordinate = Coordinate(2, 3)

        Mockito.doReturn(null).`when`(board)[landingCoordinate]
        Mockito.doReturn(myKing).`when`(board)[myCoordinate]

        val piecesInRange = listOf<Piece>()

        val result = myKing.canSimpleStep(landingCoordinate, board, piecesInRange)

        assertTrue(result)
    }

    /**
     * check if king piece can step to place taken by enemy piece.
     * EXPECTATION: no
     */
    @Test
    fun canSimpleStep_king_targetIsTakenByEnemy_no() {
        val landingCoordinate = Coordinate(5, 6)
        val myCoordinate = Coordinate(2, 3)

        Mockito.doReturn(enemyPiece).`when`(board)[landingCoordinate]
        Mockito.doReturn(myKing).`when`(board)[myCoordinate]

        val piecesInRange = listOf<Piece>()

        val result = myKing.canSimpleStep(landingCoordinate, board, piecesInRange)

        assertFalse(result)
    }

    /**
     * check if king piece can step to place taken by friendly piece.
     * EXPECTATION: no
     */
    @Test
    fun canSimpleStep_king_targetIsTakenByFriend_no() {
        val landingCoordinate = Coordinate(5, 6)
        val myCoordinate = Coordinate(2, 3)

        Mockito.doReturn(friendlyPiece).`when`(board)[landingCoordinate]
        Mockito.doReturn(myKing).`when`(board)[myCoordinate]

        val piecesInRange = listOf<Piece>()

        val result = myKing.canSimpleStep(landingCoordinate, board, piecesInRange)

        assertFalse(result)
    }

    /**
     * check if king piece can step to empty place when is enemy piece in the way.
     * EXPECTATION: no
     */
    @Test
    fun canSimpleStep_king_enemyPieceInRange_no() {
        val landingCoordinate = Coordinate(5, 6)
        val myCoordinate = Coordinate(2, 3)

        Mockito.doReturn(null).`when`(board)[landingCoordinate]
        Mockito.doReturn(myKing).`when`(board)[myCoordinate]

        val piecesInRange = listOf<Piece>(enemyPiece)

        val result = myKing.canSimpleStep(landingCoordinate, board, piecesInRange)

        assertFalse(result)
    }

    /**
     * check if king piece can step to empty place when is friendly piece in the way.
     * EXPECTATION: no
     */
    @Test
    fun canSimpleStep_king_friendlyPieceInRange_no() {
        val landingCoordinate = Coordinate(5, 6)
        val myCoordinate = Coordinate(2, 3)

        Mockito.doReturn(null).`when`(board)[landingCoordinate]
        Mockito.doReturn(myKing).`when`(board)[myCoordinate]

        val piecesInRange = listOf<Piece>(friendlyPiece)

        val result = myKing.canSimpleStep(landingCoordinate, board, piecesInRange)

        assertFalse(result)
    }
}