package Rutas

import DAOs.EspecificacionMonitorDAO.EspecificacionesMonitorDAO
import DAOs.EspecificacionMonitorDAO.EspecificacionesMonitorDAOImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.EspecificacionMonitor

val especificacionMonitorDAO: EspecificacionesMonitorDAO = EspecificacionesMonitorDAOImpl()

fun Route.rutasEspecificacionMonitor() {

        get("/{dispositivoId}") {
            val dispositivoId = call.parameters["dispositivoId"]?.toIntOrNull()
            if (dispositivoId == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }
            val especificacion = especificacionMonitorDAO.getPorDispositivoId(dispositivoId)
            if (especificacion != null) {
                call.respond(HttpStatusCode.OK, especificacion)
            } else {
                call.respond(HttpStatusCode.NotFound, "Especificación de monitor no encontrada")
            }
        }

        post("/registrar") {
            val especificacion = call.receive<EspecificacionMonitor>()
            val insertado = especificacionMonitorDAO.insertar(especificacion)
            if (insertado) {
                call.respond(HttpStatusCode.Created, true)
            } else {
                call.respond(HttpStatusCode.BadRequest, false)
            }
        }

        put("/actualizar") {
            val especificacion = call.receive<EspecificacionMonitor>()
            val actualizado = especificacionMonitorDAO.actualizar(especificacion)
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
        especificacionMonitorDAO.eliminar(dispositivoId) // ignoramos resultado
        call.respond(HttpStatusCode.OK, true)
    }
}

