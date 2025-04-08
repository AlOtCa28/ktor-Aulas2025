package DAOs.UsuarioDAO

import DAOs.Database
import modelo.Usuario


class UsuarioDAOImpl : UsuarioDAO {

    override fun obtener(nombre: String): Usuario? {
        val sql = "SELECT * FROM usuarios WHERE nombre = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, nombre)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Usuario(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    email = resultSet.getString("email"),
                    passwordHash = resultSet.getString("password_hash"),
                    rol = resultSet.getInt("rol")
                )
            }
        }
        return null
    }

    override fun obtenerUsuarioPorId(id: Int): Usuario? {
        val sql = "SELECT * FROM usuarios WHERE id_usuario = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Usuario(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    email = resultSet.getString("email"),
                    passwordHash = resultSet.getString("password_hash"),
                    rol = resultSet.getInt("rol")
                )
            }
        }
        return null
    }

    override fun registrarUsuario(usuario: Usuario): Boolean {
        val sql = "INSERT INTO usuarios (nombre, email, password_hash, rol) VALUES (?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, usuario.nombre)
            statement.setString(2, usuario.email)
            statement.setString(3, usuario.passwordHash)
            statement.setInt(4, usuario.rol)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun actualizarUsuario(usuario: Usuario): Boolean {
        val sql = "UPDATE usuarios SET nombre = ?, email = ?, password_hash = ?, rol = ? WHERE id_usuario = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, usuario.nombre)
            statement.setString(2, usuario.email)
            statement.setString(3, usuario.passwordHash)
            statement.setInt(4, usuario.rol)
            statement.setInt(5, usuario.id)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun eliminarUsuario(id: Int): Boolean {
        val sql = "DELETE FROM usuarios WHERE id_usuario = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun listarUsuarios(): List<Usuario> {
        val sql = "SELECT * FROM usuarios"
        val connection = Database.getConnection()
        val usuarios = mutableListOf<Usuario>()

        connection?.use {
            val statement = it.createStatement()
            val resultSet = statement.executeQuery(sql)

            while (resultSet.next()) {
                val usuario = Usuario(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    email = resultSet.getString("email"),
                    passwordHash = resultSet.getString("password_hash"),
                    rol = resultSet.getInt("rol")
                )
                usuarios.add(usuario)
            }
        }
        return usuarios
    }
}