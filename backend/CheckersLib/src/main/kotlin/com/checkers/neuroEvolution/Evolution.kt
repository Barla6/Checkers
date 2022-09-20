package com.checkers.neuroEvolution

import com.checkers.models.GameLevel
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class Evolution {

    private var generationsNumber = 0

    suspend fun draw() {
        var population = Population.generatePopulation(5, generationsNumber)
        while (generationsNumber <= GameLevel.HARD.generation) {
            generationsNumber++
            val gamesManager = GameManager(population)
            gamesManager.runGamesParallel()
//            population.printPopulationStats()
//            saveIfNeeded(population)
            population = population.repopulate()
        }
    }

    private fun saveIfNeeded(population: Population) {

        val level = GameLevel.getLevelStringByGeneration(generationsNumber) ?: return
        val path = GameLevel.getLevelFilePath(level)
        val dnaToSave = population.pickBest()!!.dna

        ObjectOutputStream(FileOutputStream(path)).use { it.writeObject(dnaToSave) }

        println("AI of level $level was saved!")
    }
}