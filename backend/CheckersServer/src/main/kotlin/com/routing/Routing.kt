package com.routing

import com.checkers.models.*
import com.management.GamesManager
import com.management.PlayersManager
import com.requestsClasses.NewGameRequestBody
import com.requestsClasses.PlayTurnRequestBody
import com.requestsClasses.PossibleMovesRequestBody
import com.responsesClasses.NewGameResponse
import com.responsesClasses.PossibleMoveToSend
import com.responsesClasses.createBoard
import com.responsesClasses.createBoardToSend
import io.ktor.http.*
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
                .also {
                    if (!it.isValid()) {
                        call.respond(HttpStatusCode.BadRequest, "request body is invalid")
                        return@post
                    }
                }
            val humanPlayer = HumanPlayer(requestBody.playerName)
            val humanPlayerId = PlayersManager.savePlayer(humanPlayer).toString()

            val aiPlayer = PlayersManager.getAIPlayer(requestBody.level)

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
                .also {
                    if (!it.isValid()) {
                        call.respond(HttpStatusCode.BadRequest, "request body is invalid")
                        return@post
                    }
                }

            val board: Board

            try {
                board = createBoard(requestBody.board)
            } catch (t: Throwable) {
                call.respond(HttpStatusCode.BadRequest, "player was not found in server")
                return@post
            }

            val stepSequence = StepSequence(
                startingBoard = board,
                steps = listOf(requestBody.coordinates),
                eaten = requestBody.eaten
            )

            val possibleMoves = stepSequence.getNextPossibleSteps()
                .map {
                    PossibleMoveToSend(
                        step = it.currentCoordinates,
                        board = createBoardToSend(it.resultBoard),
                        it.eaten,
                        it.completed
                    )
                }

            call.respond(possibleMoves)
        }

        post("play-turn") {
            val requestBody = call.receive<PlayTurnRequestBody>()
                .also {
                    if (!it.isValid()) {
                        call.respond(HttpStatusCode.BadRequest, "request body is invalid")
                        return@post
                    }
                }

            val game = GamesManager.getGame(requestBody.gameId.toInt()).also {
                if (it == null) {
                    call.respond(HttpStatusCode.Conflict, "game not found in server")
                    return@post
                }
            }

            val humanPlayer = PlayersManager.getPlayer(requestBody.playerId).also {
                if (it == null) {
                    call.respond(HttpStatusCode.Conflict, "player not found in server")
                    return@post
                }
            }

            game!!.playHumanPlayerTurn(requestBody.steps, humanPlayer!! as HumanPlayer)
                .runCatching {
                    call.respond(HttpStatusCode.Conflict, "player and game are not related")
                    return@post
                }

            val oppositePlayer = game.getOppositePlayer(humanPlayer).also {
                if (it == null) {
                    call.respond(HttpStatusCode.Conflict, "player and game are not related")
                    return@post
                } else if (it !is AIPlayer) {
                    call.respond(HttpStatusCode.Conflict, "the request supports only AI vs Human games")
                    return@post
                }
            }

            game.playAIPlayerTurn(oppositePlayer!! as AIPlayer).runCatching {
                call.respond(HttpStatusCode.Conflict, "player and game are not related")
                return@post
            }

            val response = createBoardToSend(game.board)

            call.respond(response)
        }
    }
}