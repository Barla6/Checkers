package com.checkers.neuroEvolution

import kotlin.random.Random

//typealias DNA = List<List<Double>>.Companion
//
//typealias DNACompanion = DNA.Companion
//
//fun DNA.of(network: NeuralNetwork): DNA {
//
//}

data class DNA(val dna: List<List<Double>>) {

    val weightsInputHidden = dna[0]
    val weightsHiddenOutput = dna[1]
    val biasesHidden = dna[2]
    val biasesOutput = dna[3]

    companion object {
        fun of(neuralNetwork: NeuralNetwork): DNA {
            return DNA(listOf(
                neuralNetwork.weightsInputHidden.data.flatten(),
                neuralNetwork.weightsHiddenOutput.data.flatten(),
                neuralNetwork.biasesHidden.data.flatten(),
                neuralNetwork.biasesOutput.data.flatten()
            ))
        }

        fun crossover(dna1: DNA, dna2: DNA): DNA {
            return DNA(dna1.dna.zip(dna2.dna).map { (dna1, dna2) ->
                val size = dna1.size
                val randomMiddle = (0 until size).random()
                dna1.subList(0, randomMiddle) + dna2.subList(randomMiddle, size)
            })
        }
    }

    fun mutate(mutationRate: Double): DNA {
        return DNA(dna.map { row ->
            row.map { value ->
                val random = Random.nextDouble(0.0, 1.0)
                if (mutationRate > random) Random.nextDouble(0.0, 1.0)
                else value
            }
        })
    }

    infix fun sameSizeAs (other: DNA): Boolean {
        return this.dna.size == other.dna.size &&
                this.dna.zip(other.dna).all { (dna1Row, dna2Row) -> dna1Row.size == dna2Row.size }
    }
}