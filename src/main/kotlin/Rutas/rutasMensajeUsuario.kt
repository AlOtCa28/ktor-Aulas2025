package Rutas

import DAOs.Mensajes.MensajeUsuarioDAO
import DAOs.Mensajes.MensajeUsuarioDAOImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.MensajeUsuario

val mensajeUsuarioDAO: MensajeUsuarioDAO = MensajeUsuarioDAOImpl()

fun Route.rutasMensajeUsuario() {
    post("/registrar") {
        val mensajeUsuario = call.receive<MensajeUsuario>()
        val registrado = mensajeUsuarioDAO.registrarMensajeUsuario(mensajeUsuario)
        if (registrado) {
            call.respond(HttpStatusCode.Created, true)
        } else {
            call.respond(HttpStatusCode.BadRequest, false)
        }
    }

    put("/marcar-mostrado") {
        val mensajeUsuario = call.receive<MensajeUsuario>()
        val marcado = mensajeUsuarioDAO.marcarMensajeMostrado(mensajeUsuario)
        if (marcado) {
            call.respond(HttpStatusCode.OK, true)
        } else {
            call.respond(HttpStatusCode.NotFound, "Registro no encontrado")
        }
    }

    get("/no-mostrados/{usuarioEmail}") {
        val usuarioEmail = call.parameters["usuarioEmail"]
        if (usuarioEmail.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, "Email inválido")
            return@get
        }
        val mensajes = mensajeUsuarioDAO.obtenerMensajesNoMostradosPorUsuario(usuarioEmail)
        call.respond(HttpStatusCode.OK, mensajes)
    }

    delete("/borrar-por-mensaje/{mensajeId}") {
        val mensajeId = call.parameters["mensajeId"]?.toLongOrNull()
        if (mensajeId == null) {
            call.respond(HttpStatusCode.BadRequest, "ID de mensaje inválido")
            return@delete
        }
        val eliminado = mensajeUsuarioDAO.eliminarPorMensajeId(mensajeId)
        if (eliminado) {
            call.respond(HttpStatusCode.OK, true)
        } else {
            call.respond(HttpStatusCode.NotFound, "No se encontró registro para eliminar")
        }
    }
}

