package com.example.com.example

import Rutas.rutasUsuario
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        // Ruta principal
        get("/") {
            call.respondText("Servidor funcionando")
        }
        route("/usuarios") {
            rutasUsuario()
        }
    }
}
