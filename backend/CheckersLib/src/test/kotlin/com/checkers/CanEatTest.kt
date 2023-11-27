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

internal class CanEatTest {

    private val myPawn = Pawn(PieceColor.WHITE)
    private val myKing = King(PieceColor.WHITE)
    private val board = Mockito.spy(Board())

    private val enemyPiece = Pawn(PieceColor.BLACK)
    private val friendlyPiece = Pawn(PieceColor.WHITE)

    @BeforeEach
    fun init() {

    }

    /**
     * check if regular piece can eat enemy piece next to it.
     * EXPECTATION: yes
     */
    @Test
    fun canEat_regularPiece_yes() {

        val landingCoordinate = Coordinate(3, 4)
        val nextCoordinate = Coordinate(2, 3)
        val myCoordinate = Coordinate(1, 2)

        Mockito.doReturn(myPawn).`when`(board)[myCoordinate]
        Mockito.doReturn(enemyPiece).`when`(board)[nextCoordinate]
        Mockito.doReturn(null).`when`(board)[landingCoordinate]

        val result = myPawn.canEat(nextCoordinate, landingCoordinate, board)

        assertTrue(result)
    }

    /**
     * check if regular piece can eat when there is no other piece.
     * EXPECTATION: no
     */
    @Test
    fun canEat_regularPiece_nothingToEat_no() {
        val landingCoordinate = Coordinate(3, 4)
        val nextCoordinate = Coordinate(2, 3)
        val myCoordinate = Coordinate(1, 2)

        Mockito.doReturn(myPawn).`when`(board)[myCoordinate]
        Mockito.doReturn(null).`when`(board)[nextCoordinate]
        Mockito.doReturn(null).`when`(board)[landingCoordinate]

        val result = myPawn.canEat(nextCoordinate, landingCoordinate, board)

        assertFalse(result)
    }

    /**
     * check if regular piece can eat friendly piece next to it.
     * EXPECTATION: no
     */
    @Test
    fun canEat_regularPiece_friendlyPieceToEat_no() {
        val landingCoordinate = Coordinate(3, 4)
        val nextCoordinate = Coordinate(2, 3)
        val myCoordinate = Coordinate(1, 2)

        Mockito.doReturn(myPawn).`when`(board)[myCoordinate]
        Mockito.doReturn(friendlyPiece).`when`(board)[nextCoordinate]
        Mockito.doReturn(null).`when`(board)[landingCoordinate]

        val result = myPawn.canEat(nextCoordinate, landingCoordinate, board)

        assertFalse(result)
    }

    /**
     * check if regular piece can eat enemy piece next to it, when the landing place is taken.
     * EXPECTATION: no
     */
    @Test
    fun canEat_regularPiece_landingPlaceTaken_no() {
        val landingCoordinate = Coordinate(3, 4)
        val nextCoordinate = Coordinate(2, 3)
        val myCoordinate = Coordinate(1, 2)

        Mockito.doReturn(myPawn).`when`(board)[myCoordinate]
        Mockito.doReturn(enemyPiece).`when`(board)[nextCoordinate]
        Mockito.doReturn(friendlyPiece).`when`(board)[landingCoordinate]

        val result = myPawn.canEat(nextCoordinate, landingCoordinate, board)

        assertFalse(result)
    }

    /**
     * check if king piece can eat enemy piece next to it.
     * EXPECTATION: yes
     */
    @Test
    fun canEat_king_simpleEating_yes() {
        val landingCoordinate = Coordinate(3, 4)
        val nextCoordinate = Coordinate(2, 3)
        val myCoordinate = Coordinate(1, 2)

        Mockito.doReturn(myKing).`when`(board)[myCoordinate]
        Mockito.doReturn(enemyPiece).`when`(board)[nextCoordinate]
        Mockito.doReturn(null).`when`(board)[landingCoordinate]

        val piecesInRange = listOf(enemyPiece)

        val result = myKing.canEat(landingCoordinate, board, piecesInRange)

        assertTrue(result)
    }

    /**
     * check if king piece can eat enemy piece distant from it.
     * EXPECTATION: yes
     */
    @Test
    fun canEat_king_simpleEatingFromDistance_yes() {
        val landingCoordinate = Coordinate(5, 6)
        val nextCoordinate = Coordinate(4, 5)
        val myCoordinate = Coordinate(1, 2)

        Mockito.doReturn(myKing).`when`(board)[myCoordinate]
        Mockito.doReturn(enemyPiece).`when`(board)[nextCoordinate]
        Mockito.doReturn(null).`when`(board)[landingCoordinate]

        val piecesInRange = listOf(enemyPiece)

        val result = myKing.canEat(landingCoordinate, board, piecesInRange)

        assertTrue(result)
    }

    /**
     * check if king piece can eat when there are no other pieces.
     * EXPECTATION: no
     */
    @Test
    fun canEat_king_nothingToEat_no() {
        val landingCoordinate = Coordinate(5, 6)
        val myCoordinate = Coordinate(1, 2)

        Mockito.doReturn(myKing).`when`(board)[myCoordinate]
        Mockito.doReturn(null).`when`(board)[landingCoordinate]

        val piecesInRange = listOf<Piece>()

        val result = myKing.canEat(landingCoordinate, board, piecesInRange)

        assertFalse(result)
    }

    /**
     * check if king piece can eat friendly piece.
     * EXPECTATION: no
     */
    @Test
    fun canEat_king_friendlyPieceToEat_no() {
        val landingCoordinate = Coordinate(5, 6)
        val nextCoordinate = Coordinate(4, 5)
        val myCoordinate = Coordinate(1, 2)

        Mockito.doReturn(myKing).`when`(board)[myCoordinate]
        Mockito.doReturn(friendlyPiece).`when`(board)[nextCoordinate]
        Mockito.doReturn(null).`when`(board)[landingCoordinate]

        val piecesInRange = listOf(friendlyPiece)

        val result = myKing.canEat(landingCoordinate, board, piecesInRange)

        assertFalse(result)
    }

    /**
     * check if king piece can eat enemy piece when there is a piece in the way.
     * EXPECTATION: no
     */
    @Test
    fun canEat_king_piecesInTheWay_no() {
        val landingCoordinate = Coordinate(5, 6)
        val myCoordinate = Coordinate(1, 2)

        Mockito.doReturn(myKing).`when`(board)[myCoordinate]
        Mockito.doReturn(enemyPiece).`when`(board)[Coordinate(4, 5)]
        Mockito.doReturn(enemyPiece).`when`(board)[Coordinate(3, 4)]
        Mockito.doReturn(null).`when`(board)[landingCoordinate]

        val piecesInRange = listOf(enemyPiece, enemyPiece)

        val result = myKing.canEat(landingCoordinate, board, piecesInRange)

        assertFalse(result)
    }

    /**
     * check if king piece can eat enemy piece when there is a piece in the way.
     * EXPECTATION: no
     */
    @Test
    fun canEat_king_landingPlaceTaken_no() {
        val landingCoordinate = Coordinate(5, 6)
        val nextCoordinate = Coordinate(4, 5)
        val myCoordinate = Coordinate(1, 2)

        Mockito.doReturn(myKing).`when`(board)[myCoordinate]
        Mockito.doReturn(enemyPiece).`when`(board)[nextCoordinate]
        Mockito.doReturn(friendlyPiece).`when`(board)[landingCoordinate]

        val piecesInRange = listOf(enemyPiece)

        val result = myKing.canEat(landingCoordinate, board, piecesInRange)

        assertFalse(result)
    }
}