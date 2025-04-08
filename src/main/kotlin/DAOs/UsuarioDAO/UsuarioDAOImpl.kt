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
                    id = resultSet.getInt("id_usuario"),
                    nombre = resultSet.getString("nombre"),
                    email = resultSet.getString("email"),
                    passwordHash = resultSet.getString("contraseña"),
                    rol = resultSet.getInt("rol")
                )
            }
        }
        return null
    }

    override fun obtenerUsuarioPorId(id: Int): Usuario? {
        TODO("Not yet implemented")
    }

    override fun registrarUsuario(usuario: Usuario): Boolean {
        TODO("Not yet implemented")
    }

    override fun actualizarUsuario(usuario: Usuario): Boolean {
        TODO("Not yet implemented")
    }

    override fun eliminarUsuario(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun listarUsuarios(): List<Usuario> {
        TODO("Not yet implemented")
    }
}