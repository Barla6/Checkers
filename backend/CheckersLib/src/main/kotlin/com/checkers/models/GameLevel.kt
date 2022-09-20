package com.checkers.models

import java.nio.file.Paths

enum class GameLevel(val generation: Int, val levelName: String) {
    EASY(1, "easy"),
    MEDIUM(20, "medium"),
    HARD(40, "hard");

    companion object {
        fun getLevelStringByGeneration(generation: Int): GameLevel? =
            values().find { gameLevel -> gameLevel.generation == generation }


        fun getLevelFilePath(level: GameLevel): String {
            val absPath = Paths.get("").toAbsolutePath().normalize().toString() +
                    "\\CheckersLib\\src\\main\\kotlin\\com\\checkers\\neuroEvolution\\trainedBrains"
            return "$absPath\\${level.levelName}.txt"
        }

        fun getGameLevelByLevelName(levelName: String): GameLevel? =
            values().find { gameLevel -> gameLevel.levelName == levelName }

    }
}