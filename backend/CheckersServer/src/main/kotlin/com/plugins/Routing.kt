package com.plugins

import com.checkers.models.Game
import com.checkers.models.HumanPlayer
import com.models.*
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
            val humanPlayer = HumanPlayer(gameRequest.playerName)
            val humanPlayerId = PlayersManager.savePlayer(humanPlayer)
            val aiPlayer = AIPlayers.players[gameRequest.level]!!
            val game = Game(humanPlayer, aiPlayer)
            val gameId = GamesManager.saveGame(game)
            call.respond(game.board.board)
        }
    }
}
