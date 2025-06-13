package Rutas

import DAOs.EspecificacionImpresoraDAO.EspecificacionesImpresoraDAO
import DAOs.EspecificacionImpresoraDAO.EspecificacionesImpresoraDAOImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.EspecificacionImpresora


val especificacionImpresoraDAO: EspecificacionesImpresoraDAO = EspecificacionesImpresoraDAOImpl()

fun Route.rutasEspecificacionImpresora() {
        get("/{dispositivoId}") {
            val dispositivoId = call.parameters["dispositivoId"]?.toIntOrNull()
            if (dispositivoId == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }
            val especificacion = especificacionImpresoraDAO.getPorDispositivoId(dispositivoId)
            if (especificacion != null) {
                call.respond(HttpStatusCode.OK, especificacion)
            } else {
                call.respond(HttpStatusCode.NotFound, "Especificación de impresora no encontrada")
            }
        }

        post("/registrar") {
            val especificacion = call.receive<EspecificacionImpresora>()
            val insertado = especificacionImpresoraDAO.insertar(especificacion)
            if (insertado) {
                call.respond(HttpStatusCode.Created, true)
            } else {
                call.respond(HttpStatusCode.BadRequest, false)
            }
        }

        put("/actualizar") {
            val especificacion = call.receive<EspecificacionImpresora>()
            val actualizado = especificacionImpresoraDAO.actualizar(especificacion)
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
        val eliminado = especificacionImpresoraDAO.eliminar(dispositivoId)
        // aunque eliminado sea false (no encontrado), devolvemos OK para idempotencia
        call.respond(HttpStatusCode.OK, true)
    }
}
