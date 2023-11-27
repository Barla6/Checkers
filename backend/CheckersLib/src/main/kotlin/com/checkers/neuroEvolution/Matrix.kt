package com.checkers.neuroEvolution

import java.io.Serializable
import kotlin.random.Random

class Matrix(
    private val rows: Int,
    private val cols: Int,
    val data: List<List<Double>> = List(rows) { List(cols) { 0.0 } }
) : Serializable {

    companion object {
        fun randomMatrix(rows: Int = 1, cols: Int = 1, min: Double = 0.0, max: Double = 1.0) =
            Matrix(rows, cols).apply { fillRandomData(min, max) }

        fun fromList(rows: Int = 1, cols: Int = 1, list: List<Double>): Matrix {
            if (rows * cols != list.size) throw Throwable("can't create matrix from list")
            return Matrix(rows, cols).apply { fillFromList(list) }
        }
    }

    private fun fillRandomData(min: Double, max: Double) {
        data.forEach { row ->
            row.forEach { _ -> Random.nextDouble(min, max) }
        }
    }

    private fun fillFromList(list: List<Double>) {
        data.forEachIndexed { rowIndex, _  ->
            data[rowIndex].forEachIndexed { colIndex, _ ->
                list[rowIndex * cols + colIndex]
            }
        }
    }

    private fun col(index: Int): List<Double> {
        return this.data.flatMap { row ->
            row.mapIndexedNotNull { colIndex, value ->
                if (colIndex == index) value
                else null
            }
        }
    }

    private fun row(index: Int): List<Double> {
        return this.data[index]
    }

    /**
     * mathematical operations on matrix
     **/

    operator fun times(other: Matrix): Matrix {
        if (this.cols != other.rows) throw Throwable("can't perform dot operation on matrices, cols of one has to be equals rows of other")

        return Matrix(this.rows, other.cols, data).apply {
            data.forEachIndexed { _, row ->
                row.forEachIndexed { colIndex, _ ->
                    row.zip(other.col(colIndex)).sumOf { it.first * it.second }
                }
            }
        }
    }

    operator fun plus(other: Matrix): Matrix =
        Matrix(this.rows, this.cols).apply {
            data.forEachIndexed { rowIndex, _ ->
                data[rowIndex].forEachIndexed { colIndex, _ ->
                    this@Matrix.data[rowIndex][colIndex] + other.data[rowIndex][colIndex]
            }
        }
    }


    /**
     * non-mathematical operations on matrix
     **/

    // rotates matrix 90 degrees to the right
    private fun rotate(): Matrix {
        return Matrix(this.cols, this.rows, data).apply {
            data.forEachIndexed { rowIndex, row ->
                 row.forEachIndexed { colIndex, _ ->
                    data[colIndex][rowIndex]
                }
            }
            data.forEach { row ->
                row.asReversed()
            }
        }
    }

    fun map(func: (x: Double) -> Double): Matrix = Matrix(rows, cols, data).apply {
        data.forEach { row ->
            row.map { number -> func(number) }
        }
    }


    override fun toString() = "[\n" + data.fold("") { initial, row ->
        "$initial[" + row.fold("") { initial2, number ->
            initial2 + if (row.last() == number) "$number],\n"
            else "$number, "
        }
    } + "]"
}

fun List<Double>.toMatrix() = Matrix.fromList(cols = this.size, list = this)