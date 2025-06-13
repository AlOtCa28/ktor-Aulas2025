package DAOs.Mensajes

import DAOs.Database
import modelo.Mensaje
import modelo.MensajeUsuario

class MensajeUsuarioDAOImpl : MensajeUsuarioDAO {

    override fun registrarMensajeUsuario(mensajeUsuario: MensajeUsuario): Boolean {
        val sql = "INSERT INTO mensajes_usuarios (mensaje_id, usuario_correo, mostrado) VALUES (?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setLong(1, mensajeUsuario.mensajeId)
            statement.setString(2, mensajeUsuario.usuarioCorreo)
            statement.setBoolean(3, mensajeUsuario.mostrado)
            return statement.executeUpdate() > 0
        }
        return false
    }



    override fun marcarMensajeMostrado(mensajeUsuario: MensajeUsuario): Boolean {
        val sql = "UPDATE mensajes_usuarios SET mostrado = TRUE WHERE mensaje_id = ? AND usuario_correo = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setLong(1, mensajeUsuario.mensajeId)
            statement.setString(2, mensajeUsuario.usuarioCorreo)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerMensajesNoMostradosPorUsuario(usuarioCorreo: String): List<Mensaje> {
        val sql = """
            SELECT m.id, m.contenido, m.fecha 
            FROM mensajes m
            INNER JOIN mensajes_usuarios mu ON m.id = mu.mensaje_id
            WHERE mu.usuario_correo = ? AND mu.mostrado = FALSE
        """.trimIndent()

        val mensajes = mutableListOf<Mensaje>()
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, usuarioCorreo)
            val rs = statement.executeQuery()
            while (rs.next()) {
                mensajes.add(
                    Mensaje(
                        id = rs.getLong("id"),
                        contenido = rs.getString("contenido"),
                        fecha = rs.getString("fecha")
                    )
                )
            }
        }
        return mensajes
    }

    override fun eliminarPorMensajeId(mensajeId: Long): Boolean {
        val sql = "DELETE FROM mensajes_usuarios WHERE mensaje_id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setLong(1, mensajeId)
            return statement.executeUpdate() > 0
        }
        return false
    }
}
