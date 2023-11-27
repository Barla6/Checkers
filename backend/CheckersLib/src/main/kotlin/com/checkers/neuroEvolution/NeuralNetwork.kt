package com.checkers.neuroEvolution

import com.checkers.models.*
import com.checkers.models.piece.King
import com.checkers.models.piece.Pawn
import java.io.FileInputStream
import java.io.ObjectInputStream
import kotlin.math.exp

class NeuralNetwork private constructor(
    val inputNodes: Int,
    val hiddenNodes: Int,
    val outputNodes: Int,
    val weightsInputHidden: Matrix,
    val weightsHiddenOutput: Matrix,
    val biasesHidden: Matrix,
    val biasesOutput: Matrix
) {

    var fitness: Double = 0.0

    val dna: DNA = DNA.of(this)

    companion object {
        fun random(inputNodes: Int, hiddenNodes: Int, outputNodes: Int): NeuralNetwork =
            NeuralNetwork(
                inputNodes, hiddenNodes, outputNodes,
                weightsInputHidden = Matrix.randomMatrix(inputNodes, hiddenNodes),
                weightsHiddenOutput = Matrix.randomMatrix(hiddenNodes, outputNodes),
                biasesHidden = Matrix.randomMatrix(cols = hiddenNodes),
                biasesOutput = Matrix.randomMatrix(cols = outputNodes)
            )

        fun fromDNA(inputNodes: Int, hiddenNodes: Int, outputNodes: Int, dna: DNA): NeuralNetwork =
            NeuralNetwork(
                inputNodes, hiddenNodes, outputNodes,
                weightsInputHidden = Matrix.fromList(inputNodes, hiddenNodes, dna.weightsInputHidden),
                weightsHiddenOutput = Matrix.fromList(hiddenNodes, outputNodes, dna.weightsHiddenOutput),
                biasesHidden = Matrix.fromList(cols = hiddenNodes, list = dna.biasesHidden),
                biasesOutput = Matrix.fromList(cols = outputNodes, list = dna.biasesOutput)
            )

        fun ofLevel(level: Level): NeuralNetwork {
            val dna = DNA(ObjectInputStream(FileInputStream(level.filePath)).use { it.readObject() } as List<List<Double>>)
            return fromDNA(32, 16, 1, dna)
        }
    }

    // activation function: sigmoid
    private fun activation(x: Double): Double = 1 / (1 + exp(-x))

    fun rate(inputBoard: Board, pieceColor: PieceColor): Double {
        return rate(createInput(inputBoard, pieceColor))
    }

    private fun rate(input: List<Double>): Double {
        if (input.size != inputNodes) throw Throwable("input size to NN is not ok")
        val inputMatrix = Matrix.fromList(cols = inputNodes, list = input)

        val hidden = inputMatrix * weightsInputHidden
        val hiddenWithBias = hidden + biasesHidden
        val hiddenAfterActivation = hiddenWithBias.map { number -> activation(number) }

        val output = hiddenAfterActivation * weightsHiddenOutput
        val outputWithBias = output + biasesOutput
        val outputAfterActivation = outputWithBias.map { number -> activation(number) }

        return outputAfterActivation.data.first().first()
    }

    private fun createInput(board: Board, pieceColor: PieceColor): List<Double> {
        return board.tiles.flatMap { row ->
            row.mapNotNull { tile ->
                if (!tile.isBlack()) return@mapNotNull null
                val value = when (tile.piece) {
                    is Pawn -> 1.0
                    is King -> 2.0
                    else -> 0.0
                }
                if (tile.piece?.pieceColor == pieceColor.enemy) return@mapNotNull -value
                return@mapNotNull value
            }
        }
    }

    operator fun times(int: Int): List<NeuralNetwork> = List(int) { this }
}
