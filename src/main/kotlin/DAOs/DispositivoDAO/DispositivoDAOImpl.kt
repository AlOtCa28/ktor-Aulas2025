package DAOs.DispositivoDAO

import DAOs.Database
import modelo.Dispositivo
import java.sql.ResultSet

class DispositivoDAOImpl : DispositivoDAO {

    private fun mapToDispositivo(result: ResultSet): Dispositivo? {
        return result.getInt("aula_id").takeIf { !result.wasNull() }?.let {
            Dispositivo(
                id = result.getInt("id"),
                codigo = result.getString("codigo"),
                descripcion = result.getString("descripcion"),
                estado = result.getString("estado"),
                marca = result.getString("marca"),
                modelo = result.getString("modelo"),
                numeroSerie = result.getString("numero_serie"),
                ubicacionReferencia = result.getString("ubicacion_referencia"),
                ubicacionActual = result.getString("ubicacion_actual"),
                aulaId = it,
                tipo = result.getString("tipo")
            )
        }
    }

    override suspend fun listarDispositivos(): List<Dispositivo> {
        val dispositivos = mutableListOf<Dispositivo>()
        val sql = "SELECT * FROM dispositivos"
        val connection = Database.getConnection()
        connection?.use {
            val result = it.prepareStatement(sql).executeQuery()
            while (result.next()) {
                mapToDispositivo(result)?.let { dispositivo ->
                    dispositivos.add(dispositivo)
                }
            }
        }
        return dispositivos
    }

    override suspend fun obtenerDispositivoPorId(id: Int): Dispositivo? {
        val sql = "SELECT * FROM dispositivos WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            val result = statement.executeQuery()
            if (result.next()) {
                return mapToDispositivo(result)
            }
        }
        return null
    }

    override suspend fun registrarDispositivo(dispositivo: Dispositivo): Boolean {
        val sql = """
            INSERT INTO dispositivos 
            (codigo, descripcion, estado, marca, modelo, numero_serie, ubicacion_referencia, ubicacion_actual, aula_id, tipo) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()

        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, dispositivo.codigo)
            statement.setString(2, dispositivo.descripcion)
            statement.setString(3, dispositivo.estado)
            statement.setString(4, dispositivo.marca)
            statement.setString(5, dispositivo.modelo)
            statement.setString(6, dispositivo.numeroSerie)
            statement.setString(7, dispositivo.ubicacionReferencia)
            statement.setString(8, dispositivo.ubicacionActual)
            if (dispositivo.aulaId != null) statement.setInt(9, dispositivo.aulaId) else statement.setNull(9, java.sql.Types.INTEGER)
            statement.setString(10, dispositivo.tipo)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override suspend fun actualizarDispositivo(dispositivo: Dispositivo): Boolean {
        if (dispositivo.id == null) return false // Validación previa

        val sql = """
            UPDATE dispositivos SET
            codigo = ?, descripcion = ?, estado = ?, marca = ?, modelo = ?, numero_serie = ?, 
            ubicacion_referencia = ?, ubicacion_actual = ?, aula_id = ?, tipo = ?
            WHERE id = ?
        """.trimIndent()

        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, dispositivo.codigo)
            statement.setString(2, dispositivo.descripcion)
            statement.setString(3, dispositivo.estado)
            statement.setString(4, dispositivo.marca)
            statement.setString(5, dispositivo.modelo)
            statement.setString(6, dispositivo.numeroSerie)
            statement.setString(7, dispositivo.ubicacionReferencia)
            statement.setString(8, dispositivo.ubicacionActual)
            if (dispositivo.aulaId != null) statement.setInt(9, dispositivo.aulaId) else statement.setNull(9, java.sql.Types.INTEGER)
            statement.setString(10, dispositivo.tipo)
            statement.setInt(11, dispositivo.id)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override suspend fun eliminarDispositivo(id: Int): Boolean {
        val sql = "DELETE FROM dispositivos WHERE id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, id)
            return statement.executeUpdate() > 0
        }
        return false
    }
}
