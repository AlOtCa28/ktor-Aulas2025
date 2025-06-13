package com.example.com.example

import Rutas.*
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
        route("/aulas") {
            rutasAulas()
        }
        route("/dispositivos") {
            rutasDispositivo()
        }

        route("/especificacion-impresora") {
            rutasEspecificacionImpresora()
        }

        route("/especificacion-monitor") {
            rutasEspecificacionMonitor()
        }

        route("/especificacion-pc") {
            rutasEspecificacionPc()
        }
    }
}
