package Rutas

import DAOs.UsuarioDAO.UsuarioDAO
import DAOs.UsuarioDAO.UsuarioDAOImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.USUARIO.UsuarioLogIn
import modelo.Usuario

val usuarioDAO: UsuarioDAO = UsuarioDAOImpl()

fun Route.rutasUsuario() {
    route("/listado") {
        get {
            if (usuarioDAO.listarUsuarios().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, usuarioDAO.listarUsuarios())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, "No hay usuarios registrados")
            }
        }
    }

    // Iniciar sesión
    route("/login") {
        post {
            val user = call.receive<UsuarioLogIn>()
            val usuario = usuarioDAO.obtener(user.nombre) ?: return@post call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
            if (usuario.passwordHash != user.contraseña) {
                return@post call.respond(HttpStatusCode.BadRequest, "Contraseña incorrecta")
            }
            call.respond(HttpStatusCode.OK, usuario)
        }
    }
}