package com.checkers.neuroEvolution

import com.checkers.utlis.asyncMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.lang.Error
import kotlin.math.roundToInt

class Population(
    val brains: List<NeuralNetwork>
) {

    init {
        if (brains.size < 2) throw Error("Population must be 2 or more")
    }

    private val scope = CoroutineScope(Dispatchers.Default)
    private val selectionPool = initSelectionPool()

    val best: NeuralNetwork
        get() = brains.maxByOrNull { it.fitness }!!

    /**
     * create list of neural networks that holds the current population,
     * so that neural network with higher fitness will appear more time than neural network with lower fitness
     * **/
    private fun initSelectionPool(): List<NeuralNetwork> {
        return brains.map { nn -> nn * nn.fitness.roundToInt() }.flatten()
    }

    companion object {
        private const val MUTATION_RATE = 0.01

        fun random(amount: Int): Population {
            return Population(List(amount) {
                NeuralNetwork.random(32, 16, 1)
            })
        }

        private fun mutate(original: NeuralNetwork): NeuralNetwork {

            return NeuralNetwork.fromDNA(
                original.inputNodes,
                original.hiddenNodes,
                original.outputNodes,
                original.dna.mutate(MUTATION_RATE)
            )
        }

        private fun crossover(parent1: NeuralNetwork, parent2: NeuralNetwork): NeuralNetwork {
            val dna1 = parent1.dna
            val dna2 = parent2.dna

            if (!(dna1 sameSizeAs dna2)) throw Throwable("can't perform crossover on different sizes DNAs")

            return NeuralNetwork.fromDNA(
                parent1.inputNodes,
                parent1.hiddenNodes,
                parent1.outputNodes,
                DNA.crossover(dna1, dna2)
            )
        }
    }

    suspend fun repopulate(): Population {
        return Population(brains.indices
            .asyncMap(scope) { selectParents() }
            .asyncMap(scope) { parents -> crossover(parents.first, parents.second) }
            .asyncMap(scope) { child -> mutate(child) })
    }

    private fun selectParents(): Pair<NeuralNetwork, NeuralNetwork> {
        val parent1 = selectParent()
        val parent2 = selectParent(partner = parent1)
        return parent1 to parent2
    }

    private fun selectParent(partner: NeuralNetwork? = null): NeuralNetwork {
        var randomParent = selectionPool.random()
        while (randomParent == partner) randomParent = selectionPool.random()
        return randomParent
    }

    fun printPopulationStats(generationNumber: Int) {
        println("    name    |  fitness  ")
        brains.forEachIndexed { index, nn ->
            println("   g$generationNumber-i$index   |   ${nn.fitness}")
        }
    }
}
