package com.plugins

import com.checkers.models.AIPlayer
import com.checkers.models.Game
import com.checkers.models.GameLevel
import com.checkers.models.HumanPlayer
import com.models.GameRequest
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

fun Application.configureRouting() {

    // Starting point for a Ktor app:
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/new-game") {
            val gameRequest = call.receive<GameRequest>()
            val gameLevel = GameLevel.getGameLevelByLevelName(gameRequest.level) ?: return@post
            val player = HumanPlayer(gameRequest.playerName)
            val ai = AIPlayer(gameLevel, "1")
            val game = Game(player, ai)
        }
    }
}
