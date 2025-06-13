package DAOs.Mensajes

import modelo.Mensaje
import modelo.MensajeUsuario

interface MensajeUsuarioDAO {
    fun registrarMensajeUsuario(mensajeUsuario: MensajeUsuario): Boolean
    fun marcarMensajeMostrado(mensajeUsuario: MensajeUsuario): Boolean
    fun obtenerMensajesNoMostradosPorUsuario(usuarioCorreo: String): List<Mensaje>
    fun eliminarPorMensajeId(mensajeId: Long): Boolean
}