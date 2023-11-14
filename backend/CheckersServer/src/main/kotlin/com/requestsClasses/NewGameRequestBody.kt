package com.requestsClasses

data class NewGameRequestBody(val level: String, val playerName: String) : RequestBody() {
    override fun isValid(): Boolean {
        return level != null
                && playerName != null
    }
}