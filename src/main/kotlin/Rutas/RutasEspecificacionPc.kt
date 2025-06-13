package Rutas

import DAOs.EspecificacionPcDAO.EspecificacionesPcDAO
import DAOs.EspecificacionPcDAO.EspecificacionesPcDAOImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.EspecificacionPC

val especificacionPcDAO: EspecificacionesPcDAO = EspecificacionesPcDAOImpl()

fun Route.rutasEspecificacionPc() {

        get("/{dispositivoId}") {
            val dispositivoId = call.parameters["dispositivoId"]?.toIntOrNull()
            if (dispositivoId == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }
            val especificacion = especificacionPcDAO.getPorDispositivoId(dispositivoId)
            if (especificacion != null) {
                call.respond(HttpStatusCode.OK, especificacion)
            } else {
                call.respond(HttpStatusCode.NotFound, "Especificación de PC no encontrada")
            }
        }

        post("/registrar") {
            val especificacion = call.receive<EspecificacionPC>()
            val insertado = especificacionPcDAO.insertar(especificacion)
            if (insertado) {
                call.respond(HttpStatusCode.Created, true)
            } else {
                call.respond(HttpStatusCode.BadRequest, false)
            }
        }

        put("/actualizar") {
            val especificacion = call.receive<EspecificacionPC>()
            val actualizado = especificacionPcDAO.actualizar(especificacion)
            if (actualizado) {
                call.respond(HttpStatusCode.OK, true)
            } else {
                call.respond(HttpStatusCode.BadRequest, false)
            }
        }

    delete("/eliminar/{dispositivoId}") {
        val dispositivoId = call.parameters["dispositivoId"]?.toIntOrNull()
        if (dispositivoId == null) {
            call.respond(HttpStatusCode.BadRequest, "ID inválido")
            return@delete
        }
        especificacionPcDAO.eliminar(dispositivoId) // ignoramos el resultado
        call.respond(HttpStatusCode.OK, true)
    }
    }

