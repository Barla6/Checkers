package com

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.routing.configureRouting
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            gson()
        }
        configureRouting()
    }.start(wait = true)
}
