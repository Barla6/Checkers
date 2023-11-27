package com.checkers.neuroEvolution

import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.nio.file.Paths

class Evolution(private val generations: Int, private val population: Int) {

    private var generationsNumber = 0

    suspend fun draw() {
        var population = Population.random(population)
        while (generationsNumber <= generations) {
            generationsNumber++
            val gamesManager = GameManager(population, generationsNumber)
            gamesManager.runGamesParallel()
            population.printPopulationStats(generationsNumber)
//            saveIfNeeded(population)
//            save(population)
            population = population.repopulate()
        }
    }

    private fun save(population: Population) {
        val path = "${
            Paths.get("").toAbsolutePath().normalize()
        }\\CheckersLib\\src\\main\\kotlin\\com\\checkers\\neuroEvolution\\trainedForTry"
        val dnaToSave = population.best.dna
        val file = File(path, "\\${generationsNumber}.txt")
        file.createNewFile()
        ObjectOutputStream(FileOutputStream(file)).use { it.writeObject(dnaToSave) }
        println("AI of generation $generationsNumber was saved!")
    }
}