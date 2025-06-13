package Rutas

import DAOs.Mensajes.MensajeDAO
import DAOs.Mensajes.MensajeDAOImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Mensaje
import kotlin.text.get

val mensajeDAO: MensajeDAO = MensajeDAOImpl()

fun Route.rutasMensaje() {
    route("/listado") {
        get {
            val mensajes = mensajeDAO.listarMensajes()
            if (mensajes.isNotEmpty()) {
                call.respond(HttpStatusCode.OK, mensajes)
            } else {
                call.respond(HttpStatusCode.NotFound, "No hay mensajes registrados")
            }
        }
    }

    get("/{id}") {
        val id = call.parameters["id"]?.toLongOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "ID inválido")
            return@get
        }
        val mensaje = mensajeDAO.obtenerPorId(id)
        if (mensaje != null) {
            call.respond(HttpStatusCode.OK, mensaje)
        } else {
            call.respond(HttpStatusCode.NotFound, "Mensaje no encontrado")
        }
    }

    post("/registrar") {
        val mensaje = call.receive<Mensaje>()
        val registrado = mensajeDAO.registrarMensaje(mensaje)
        if (registrado) {
            call.respond(HttpStatusCode.Created, true)
        } else {
            call.respond(HttpStatusCode.BadRequest, false)
        }
    }

    put("/actualizar") {
        val mensaje = call.receive<Mensaje>()
        if (mensaje.id == null) {
            call.respond(HttpStatusCode.BadRequest, "El ID es obligatorio para actualizar")
            return@put
        }
        val actualizado = mensajeDAO.actualizarMensaje(mensaje)
        if (actualizado) {
            call.respond(HttpStatusCode.OK, true)
        } else {
            call.respond(HttpStatusCode.NotFound, "Mensaje no encontrado para actualizar")
        }
    }

    delete("/borrar/{id}") {
        val id = call.parameters["id"]?.toLongOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "ID inválido")
            return@delete
        }
        val eliminado = mensajeDAO.eliminarMensaje(id)
        if (eliminado) {
            call.respond(HttpStatusCode.OK, true)
        } else {
            call.respond(HttpStatusCode.NotFound, "Mensaje no encontrado")
        }
    }
}
