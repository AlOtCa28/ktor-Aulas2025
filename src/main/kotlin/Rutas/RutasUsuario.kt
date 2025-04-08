package Rutas

import DAOs.UsuarioDAO.UsuarioDAO
import DAOs.UsuarioDAO.UsuarioDAOImpl
import io.ktor.server.routing.*

val usuarioDAO: UsuarioDAO = UsuarioDAOImpl()

fun Route.rutasUsuario() {

}