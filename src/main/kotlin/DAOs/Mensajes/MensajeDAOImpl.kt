package DAOs.Mensajes

import DAOs.Database
import modelo.Mensaje

class MensajeDAOImpl : MensajeDAO {

    override fun obtenerPorId(id: Long): Mensaje? {
        val sql = "SELECT * FROM mensajes WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setLong(1, id)
            val rs = statement.executeQuery()
            if (rs.next()) {
                return Mensaje(
                    id = rs.getLong("id"),
                    contenido = rs.getString("contenido"),
                    fecha = rs.getString("fecha")
                )
            }
        }
        return null
    }

    override fun registrarMensaje(mensaje: Mensaje): Boolean {
        val sql = "INSERT INTO mensajes (contenido, fecha) VALUES (?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, mensaje.contenido)
            statement.setString(2, mensaje.fecha)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun actualizarMensaje(mensaje: Mensaje): Boolean {
        val sql = "UPDATE mensajes SET contenido = ?, fecha = ? WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, mensaje.contenido)
            statement.setString(2, mensaje.fecha)
            statement.setLong(3, mensaje.id ?: return false)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun eliminarMensaje(id: Long): Boolean {
        val sql = "DELETE FROM mensajes WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setLong(1, id)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun listarMensajes(): List<Mensaje> {
        val sql = "SELECT * FROM mensajes"
        val mensajes = mutableListOf<Mensaje>()
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.createStatement()
            val rs = statement.executeQuery(sql)
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
}
