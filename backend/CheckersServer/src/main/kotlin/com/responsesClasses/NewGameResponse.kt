package com.responsesClasses

data class NewGameResponse(val gameId: Int,
                           val humanPlayerId: Int,
                           val aiPlayerId: Int,
                           val board: BoardToSend
)