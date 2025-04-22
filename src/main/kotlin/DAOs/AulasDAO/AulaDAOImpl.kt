package DAOs.AulasDAO

import DAOs.Database
import modelo.Aula

class AulaDAOImpl : AulaDAO {

    override fun obtenerAulaPorId(id: Int): Aula? {
        val sql = "SELECT * FROM aulas WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Aula(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    idEncargado = resultSet.getInt("id_encargado")
                )
            }
        }
        return null
    }

    override fun obtenerAulaPorNombre(nombre: String): Aula? {
        val sql = "SELECT * FROM aulas WHERE nombre = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, nombre)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                return Aula(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    idEncargado = resultSet.getInt("id_encargado")
                )
            }
        }
        return null
    }

    override fun registrarAula(aula: Aula): Boolean {
        val sql = "INSERT INTO aulas (nombre, id_encargado) VALUES (?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, aula.nombre)
            statement.setInt(2, aula.idEncargado ?: 0) // Asignar 0 si idEncargado es null
            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun actualizarAula(aula: Aula): Boolean {
        val sql = "UPDATE aulas SET nombre = ?, id_encargado = ? WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, aula.nombre)
            statement.setInt(2, aula.idEncargado ?: 0) // Asignar 0 si idEncargado es null
            statement.setInt(3, aula.id)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun eliminarAula(id: Int): Boolean {
        val sql = "DELETE FROM aulas WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun listarAulas(): List<Aula> {
        val sql = "SELECT * FROM aulas"
        val connection = Database.getConnection()
        val aulas = mutableListOf<Aula>()
        connection?.use {
            val statement = it.prepareStatement(sql)
            val resultSet = statement.executeQuery()

            while (resultSet.next()) {
                val aula = Aula(
                    id = resultSet.getInt("id"),
                    nombre = resultSet.getString("nombre"),
                    idEncargado = resultSet.getInt("id_encargado")
                )
                aulas.add(aula)
            }
        }
        return aulas
    }

}