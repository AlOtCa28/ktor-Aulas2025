package Rutas

import DAOs.AulasDAO.AulaDAO
import DAOs.AulasDAO.AulaDAOImpl
import DAOs.UsuarioDAO.UsuarioDAO
import DAOs.UsuarioDAO.UsuarioDAOImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Aula

val aulasDAO: AulaDAO = AulaDAOImpl()

fun Route.rutasAulas() {
    route("/listado") {
        get {
            if (aulasDAO.listarAulas().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, aulasDAO.listarAulas())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, "No hay aulas registradas")
            }
        }

        // Obtener aula por ID
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val aula = aulasDAO.obtenerAulaPorId(id)
                if (aula != null) {
                    return@get call.respond(HttpStatusCode.OK, aula)
                } else {
                    return@get call.respond(HttpStatusCode.NotFound, "Aula no encontrada")
                }
            } else {
                return@get call.respond(HttpStatusCode.BadRequest, "ID inválido")
            }
        }

        //registrar aula
        post("/registrar") {
            val aula = call.receive<Aula>()
            val registrado = aulasDAO.registrarAula(aula)
            if (registrado) {
                call.respond(HttpStatusCode.Created, true)
            } else {
                call.respond(HttpStatusCode.BadRequest, false)
            }
        }

        // Actualizar aula

        put("/actualizar") {
            val aula = call.receive<Aula>()
            if (aula.id == null) {
                return@put call.respond(HttpStatusCode.BadRequest, "El ID es obligatorio para actualizar")
            }
            val actualizado = aulasDAO.actualizarAula(aula)
            if (actualizado) {
                call.respond(HttpStatusCode.OK, true)
            } else {
                call.respond(HttpStatusCode.NotFound, "Aula no encontrada para actualizar")
            }
        }


        // Eliminar aula

        delete("/eliminar/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val eliminado = aulasDAO.eliminarAula(id)
                if (eliminado) {
                    call.respond(HttpStatusCode.OK, true)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Aula no encontrada para eliminar")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
            }
        }
    }
}