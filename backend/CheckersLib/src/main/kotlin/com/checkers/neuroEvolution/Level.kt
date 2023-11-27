package com.checkers.neuroEvolution

import java.nio.file.Paths

enum class Level {
    EASY {
        override val filePath: String
            get() = "$absPath\\EASY.txt"
    },
    MEDIUM {
        override val filePath: String
            get() = "$absPath\\MEDIUM.txt"
    },
    HARD {
        override val filePath: String
            get() = "$absPath\\HARD.txt"
    };

    companion object {
        val absPath = Paths.get("").toAbsolutePath().normalize().toString() +
                "\\CheckersLib\\src\\main\\kotlin\\com\\checkers\\neuroEvolution\\trainedBrains"

        fun getGameLevelByLevelName(levelName: String): Level? =
            values().find { level -> level.name == levelName }
    }

    abstract val filePath: String
}