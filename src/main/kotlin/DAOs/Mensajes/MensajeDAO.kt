package DAOs.Mensajes

import modelo.Mensaje

interface MensajeDAO {
    fun obtenerPorId(id: Long): Mensaje?
    fun registrarMensaje(mensaje: Mensaje): Boolean
    fun actualizarMensaje(mensaje: Mensaje): Boolean
    fun eliminarMensaje(id: Long): Boolean
    fun listarMensajes(): List<Mensaje>
}
