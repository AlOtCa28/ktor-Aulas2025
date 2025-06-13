package Rutas

import DAOs.DispositivoDAO.DispositivoDAO
import DAOs.DispositivoDAO.DispositivoDAOImpl
import DAOs.Mensajes.MensajeDAO
import DAOs.Mensajes.MensajeDAOImpl
import DAOs.Mensajes.MensajeUsuarioDAO
import DAOs.Mensajes.MensajeUsuarioDAOImpl
import DAOs.UsuarioDAO.UsuarioDAO
import DAOs.UsuarioDAO.UsuarioDAOImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Dispositivo
import modelo.Mensaje
import modelo.MensajeUsuario
import java.time.LocalDateTime

val dispositivoDAO: DispositivoDAO = DispositivoDAOImpl()

fun Route.rutasDispositivo() {

    get("/listado") {
        val dispositivos = dispositivoDAO.listarDispositivos()
        if (dispositivos.isNotEmpty()) {
            call.respond(HttpStatusCode.OK, dispositivos)
        } else {
            call.respond(HttpStatusCode.NotFound, "No hay dispositivos registrados")
        }
    }

    get("/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "ID inválido")
            return@get
        }
        val dispositivo = dispositivoDAO.obtenerDispositivoPorId(id)
        if (dispositivo != null) {
            call.respond(HttpStatusCode.OK, dispositivo)
        } else {
            call.respond(HttpStatusCode.NotFound, "Dispositivo no encontrado")
        }
    }


    // oBTENER DISPOSITIVO POR AULA
    get("/porAula/{aulaId}") {
        val aulaId = call.parameters["aulaId"]?.toIntOrNull()
        if (aulaId == null) {
            call.respond(HttpStatusCode.BadRequest, "ID de aula inválido")
            return@get
        }
        val dispositivos = dispositivoDAO.obtenerDispositivoPorAulaId(aulaId)
        if (dispositivos.isNotEmpty()) {
            call.respond(HttpStatusCode.OK, dispositivos)
        } else {
            call.respond(HttpStatusCode.NotFound, "No hay dispositivos registrados en esta aula")
        }
    }

    post("/registrar") {
        val dispositivo = call.receive<Dispositivo>()
        val registrado = dispositivoDAO.registrarDispositivo(dispositivo)
        if (registrado) {
            call.respond(HttpStatusCode.Created, true)
        } else {
            call.respond(HttpStatusCode.BadRequest, false)
        }
    }

    put("/actualizar") {
        val dispositivo = call.receive<Dispositivo>()
        if (dispositivo.id == null) {
            call.respond(HttpStatusCode.BadRequest, "El ID es obligatorio para actualizar")
            return@put
        }
        val actualizado = dispositivoDAO.actualizarDispositivo(dispositivo)
        if (actualizado) {
            call.respond(HttpStatusCode.OK, true)
        } else {
            call.respond(HttpStatusCode.NotFound, "Dispositivo no encontrado para actualizar")
        }
    }

    delete("/eliminar/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "ID inválido")
            return@delete
        }

        especificacionImpresoraDAO.eliminar(id)
        especificacionMonitorDAO.eliminar(id)
        especificacionPcDAO.eliminar(id)

        // 2. Eliminar dispositivo
        val eliminado = dispositivoDAO.eliminarDispositivo(id)

        if (eliminado) {
            call.respond(HttpStatusCode.OK, true)
        } else {
            call.respond(HttpStatusCode.NotFound, false)
        }
    }
}
