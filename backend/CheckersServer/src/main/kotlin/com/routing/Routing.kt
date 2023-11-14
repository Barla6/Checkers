package com.routing

import com.checkers.models.*
import com.management.GamesManager
import com.management.PlayersManager
import com.requestsClasses.GetTurnRequestBody
import com.requestsClasses.NewGameRequestBody
import com.requestsClasses.PlayTurnRequestBody
import com.requestsClasses.PossibleMovesRequestBody
import com.responsesClasses.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory

fun Application.configureRouting() {

    // Starting point for a Ktor app:
    routing {

        get("/") {
            call.respondText("Hello World!")
        }

        post("/new-game") {

            val logger = LoggerFactory.getLogger("/new-game")
            logger.info("handling new request")

            val requestBody = call.receive<NewGameRequestBody>()
                .also {
                    if (!it.isValid()) {
                        logger.error("request body is not valid - sending response with status BadRequest")
                        call.respond(HttpStatusCode.BadRequest, "request body is not valid")
                        return@post
                    } else if (GameLevel.getGameLevelByLevelName(it.level) != null) {
                        logger.error("game level: ${it.level} is not valid - sending response with status BadRequest")
                        call.respond(HttpStatusCode.BadRequest, "game level is not valid")
                        return@post
                    }
                }

            val gameLevel = GameLevel.getGameLevelByLevelName(requestBody.level)!!

            val humanPlayer = HumanPlayer(requestBody.playerName)
            val humanPlayerIndex = PlayersManager.registerPlayer(humanPlayer)

            val aiPlayer = AIPlayer(gameLevel, requestBody.level)
            val aiPlayerIndex = PlayersManager.registerPlayer(aiPlayer)

            val game = Game(humanPlayer, aiPlayer)
            val gameIndex = GamesManager.registerGame(game)

            val boardToSend = createBoardToSend(game.board)

            val response = NewGameResponse(
                gameIndex,
                humanPlayerIndex,
                aiPlayerIndex,
                boardToSend
            )

            call.respond(HttpStatusCode.OK, response)
        }

        post("/possible-moves") {

            val logger = LoggerFactory.getLogger("/possible-moves")
            logger.info("handling new request")

            val requestBody = call.receive<PossibleMovesRequestBody>()
                .also {
                    if (!it.isValid()) {
                        logger.error("request body is not valid - sending response with status BadRequest")
                        call.respond(HttpStatusCode.BadRequest, "request body is invalid")
                        return@post
                    } else if (!it.coordinates.insideBoard()) {
                        logger.error("coordinate is not in board - sending response with status Conflict")
                        call.respond(HttpStatusCode.Conflict, "coordinate is not in board")
                        return@post
                    } else if (it.turnProgress.any { coordinates -> !coordinates.insideBoard() }) {
                        logger.error("turn progress is not in board - sending response with status Conflict")
                        call.respond(HttpStatusCode.Conflict, "turn progress is not in board")
                        return@post
                    }
                }

            val board = createBoard(requestBody.board)

            val stepSequence = StepSequence(
                startingBoard = board,
                steps = requestBody.turnProgress,
                eaten = requestBody.eaten
            )

            try {
                val response = stepSequence.getNextPossibleSteps()
                    .map {
                        PossibleMoveToSend(
                            targetCoordinates = it.currentCoordinates,
                            board = createBoardToSend(it.resultBoard),
                            eaten = it.eaten,
                            completed = it.completed || it.getNextPossibleSteps().isEmpty()
                        )
                    }
                call.respond(HttpStatusCode.OK, response)

            } catch (error: Throwable) {
                logger.error(error.message)
                call.respond(HttpStatusCode.InternalServerError, error)
            }
        }

        post("/play-turn") {

            val logger = LoggerFactory.getLogger("/play-turn")
            logger.info("handling new request")

            val requestBody = call.receive<PlayTurnRequestBody>()
                .also {
                    if (!it.isValid()) {
                        logger.error("request body is not valid - sending response with status BadRequest")
                        call.respond(HttpStatusCode.BadRequest, "request body is invalid")
                        return@post
                    } else if (it.turnProgress.size < 2) {
                        logger.error("turn progress is shorter than 2 coordinates - sending response with status Conflict")
                        call.respond(HttpStatusCode.Conflict, "turn progress must be 2 or more coordinates")
                        return@post
                    }
                }

            val game = GamesManager.getGame(requestBody.gameId).also {
                if (it == null) {
                    logger.error("game with id: ${requestBody.gameId} not found in server")
                    call.respond(HttpStatusCode.Conflict, "game not found in server")
                    return@post
                }
            }!!

            val humanPlayer = PlayersManager.getPlayer(requestBody.playerId).also {
                if (it == null || it !is HumanPlayer) {
                    call.respond(HttpStatusCode.Conflict, "player not found in server")
                    return@post
                }
            } as HumanPlayer

            game.playHumanPlayerTurn(requestBody.turnProgress, humanPlayer)

            call.respond(HttpStatusCode.OK)
        }

        post("/get-turn") {

            val logger = LoggerFactory.getLogger("/get-turn")
            logger.info("handling new request")

            val requestBody = call.receive<GetTurnRequestBody>()
                .also {
                    if (!it.isValid()) {
                        logger.error("request body is invalid - sending response with status BadRequest")
                        call.respond(HttpStatusCode.BadRequest, "request body is invalid")
                        return@post
                    }
                }

            val game = GamesManager.getGame(requestBody.gameId).also {
                if (it == null) {
                    logger.error("game with id: ${requestBody.gameId} not found in server")
                    call.respond(HttpStatusCode.Conflict, "game not found in server")
                    return@post
                }
            }!!

            val aiPlayer = PlayersManager.getPlayer(requestBody.playerId).also {
                if (it == null || it !is AIPlayer) {
                    call.respond(HttpStatusCode.Conflict, "player not found in server")
                    return@post
                }
            } as AIPlayer

            game.playAIPlayerTurn(aiPlayer)

            val response = GetTurnResponse(
                board = createBoardToSend(game.board),
                winner = game.winner?.id
            )

            call.respond(HttpStatusCode.OK, response)
        }
    }
}