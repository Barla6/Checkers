package com.routing

import com.checkers.models.Game
import com.checkers.models.HumanPlayer
import com.checkers.models.PlayerDirection
import com.checkers.models.StepSequence
import com.management.AIPlayers
import com.management.GamesManager
import com.management.PlayersManager
import com.requestsClasses.NewGameRequestBody
import com.requestsClasses.PossibleMovesRequestBody
import com.responsesClasses.NewGameResponse
import com.responsesClasses.PossibleMoveToSend
import com.responsesClasses.createBoardToSend
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
            val requestBody = call.receive<NewGameRequestBody>()

            val humanPlayer = HumanPlayer(requestBody.playerName)
            val humanPlayerId = PlayersManager.savePlayer(humanPlayer).toString()

            val aiPlayer = AIPlayers.getPlayer(requestBody.level)

            val game = Game(humanPlayer, aiPlayer)
            val gameId = GamesManager.saveGame(game).toString()

            val response = NewGameResponse(
                gameId,
                humanPlayerId,
                turnBoard = humanPlayer.playerDirection == PlayerDirection.DOWNWARDS,
                board = createBoardToSend(game.board)
            )
            call.respond(response)
        }

        post("/possible-moves") {
            val requestBody = call.receive<PossibleMovesRequestBody>()
            val gameId = requestBody.gameId
            // todo: return status code of error
            val game = GamesManager.getGame(gameId.toInt())!!
            val stepSequence = StepSequence(game.board, listOf(requestBody.coordinates))
            val possibleMoves = stepSequence.getPossibleTurnsForPieceAsync()
                    .map { PossibleMoveToSend(it.steps, it.resultBoard) }
            call.respond(possibleMoves)
        }

        get("/board/{gameId}/{playerId}") {

        }
    }
}