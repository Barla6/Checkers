package com.checkers.neuroEvolution

import com.checkers.models.GameLevel
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.nio.file.Paths

class Evolution {

    private var generationsNumber = 0

    suspend fun draw() {
        var population = Population.generatePopulation(20, generationsNumber)
        while (generationsNumber <= 100) {
            generationsNumber++
            val gamesManager = GameManager(population)
            gamesManager.runGamesParallel()
            population.printPopulationStats()
//            saveIfNeeded(population)
            save(population)
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

    private fun save(population: Population) {
        val path = "${Paths.get("").toAbsolutePath().normalize()}\\CheckersLib\\src\\main\\kotlin\\com\\checkers\\neuroEvolution\\trainedForTry"
        val dnaToSave = population.pickBest()!!.dna
        val file = File(path, "\\${generationsNumber}.txt")
        file.createNewFile()
        ObjectOutputStream(FileOutputStream(file)).use { it.writeObject(dnaToSave) }
        println("AI of generation $generationsNumber was saved!")
    }
}