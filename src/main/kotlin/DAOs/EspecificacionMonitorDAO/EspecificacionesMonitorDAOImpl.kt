package DAOs.EspecificacionMonitorDAO

import DAOs.Database
import modelo.EspecificacionMonitor

class EspecificacionesMonitorDAOImpl : EspecificacionesMonitorDAO {

    override suspend fun getPorDispositivoId(idDispositivo: Int): EspecificacionMonitor? {
        val sql = "SELECT * FROM especificaciones_monitor WHERE dispositivo_id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idDispositivo)
            val result = statement.executeQuery()
            if (result.next()) {
                return EspecificacionMonitor(
                    dispositivoId = result.getInt("dispositivo_id"),
                    resolucionMaxima = result.getString("resolucion_maxima"),
                    pulgadas = result.getDouble("pulgadas").takeIf { !result.wasNull() },
                    orientable = result.getBoolean("orientable").takeIf { !result.wasNull() }
                )
            }
        }
        return null
    }

    override suspend fun insertar(especificacionesMonitor: EspecificacionMonitor): Boolean {
        val sql = """
            INSERT INTO especificaciones_monitor 
            (dispositivo_id, resolucion_maxima, pulgadas, orientable) 
            VALUES (?, ?, ?, ?)
        """.trimIndent()

        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, especificacionesMonitor.dispositivoId)
            statement.setString(2, especificacionesMonitor.resolucionMaxima)
            if (especificacionesMonitor.pulgadas != null)
                statement.setDouble(3, especificacionesMonitor.pulgadas)
            else
                statement.setNull(3, java.sql.Types.DOUBLE)
            if (especificacionesMonitor.orientable != null)
                statement.setBoolean(4, especificacionesMonitor.orientable)
            else
                statement.setNull(4, java.sql.Types.BOOLEAN)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override suspend fun actualizar(especificacionesMonitor: EspecificacionMonitor): Boolean {
        val sql = """
            UPDATE especificaciones_monitor SET 
            resolucion_maxima = ?, pulgadas = ?, orientable = ?
            WHERE dispositivo_id = ?
        """.trimIndent()

        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, especificacionesMonitor.resolucionMaxima)
            if (especificacionesMonitor.pulgadas != null)
                statement.setDouble(2, especificacionesMonitor.pulgadas)
            else
                statement.setNull(2, java.sql.Types.DOUBLE)
            if (especificacionesMonitor.orientable != null)
                statement.setBoolean(3, especificacionesMonitor.orientable)
            else
                statement.setNull(3, java.sql.Types.BOOLEAN)
            statement.setInt(4, especificacionesMonitor.dispositivoId)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override suspend fun eliminar(idDispositivo: Int): Boolean {
        val sql = "DELETE FROM especificaciones_monitor WHERE dispositivo_id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idDispositivo)
            return statement.executeUpdate() > 0
        }
        return false
    }
}
