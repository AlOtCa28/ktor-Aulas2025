package DAOs.UsuarioDAO

import modelo.Usuario

interface UsuarioDAO {
    fun obtener(nombre: String): Usuario?
    fun obtenerUsuarioPorId(id: Int): Usuario?
    fun registrarUsuario(usuario: Usuario): Boolean
    fun actualizarUsuario(usuario: Usuario): Boolean
    fun eliminarUsuario(id: Int): Boolean
    fun listarUsuarios(): List<Usuario>
}