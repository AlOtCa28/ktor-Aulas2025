package DAOs.EspecificacionImpresoraDAO

import DAOs.Database
import modelo.EspecificacionImpresora

class EspecificacionesImpresoraDAOImpl : EspecificacionesImpresoraDAO {

    override suspend fun getPorDispositivoId(idDispositivo: Int): EspecificacionImpresora? {
        val sql = "SELECT * FROM especificaciones_impresora WHERE dispositivo_id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idDispositivo)
            val result = statement.executeQuery()
            if (result.next()) {
                return EspecificacionImpresora(
                    dispositivoId = result.getInt("dispositivo_id"),
                    tipo = result.getString("tipo")
                )
            }
        }
        return null
    }

    override suspend fun insertar(especificacionesImpresora: EspecificacionImpresora): Boolean {
        val sql = "INSERT INTO especificaciones_impresora (dispositivo_id, tipo) VALUES (?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, especificacionesImpresora.dispositivoId)
            statement.setString(2, especificacionesImpresora.tipo)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override suspend fun actualizar(especificacionesImpresora: EspecificacionImpresora): Boolean {
        val sql = "UPDATE especificaciones_impresora SET tipo = ? WHERE dispositivo_id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, especificacionesImpresora.tipo)
            statement.setInt(2, especificacionesImpresora.dispositivoId)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override suspend fun eliminar(idDispositivo: Int): Boolean {
        val sql = "DELETE FROM especificaciones_impresora WHERE dispositivo_id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idDispositivo)
            return statement.executeUpdate() > 0
        }
        return false
    }
}
