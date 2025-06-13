package DAOs.EspecificacionPcDAO

import DAOs.Database
import modelo.EspecificacionPC

class EspecificacionesPcDAOImpl : EspecificacionesPcDAO {

    override suspend fun getPorDispositivoId(idDispositivo: Int): EspecificacionPC? {
        val sql = "SELECT * FROM especificaciones_pc WHERE dispositivo_id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idDispositivo)
            val result = statement.executeQuery()
            if (result.next()) {
                return EspecificacionPC(
                    dispositivoId = result.getInt("dispositivo_id"),
                    cpu = result.getString("cpu"),
                    ram = result.getString("ram"),
                    hd = result.getString("hd"),
                    sistemaOperativo = result.getString("sistema_operativo")
                )
            }
        }
        return null
    }

    override suspend fun insertar(especificacionesPc: EspecificacionPC): Boolean {
        val sql = """
            INSERT INTO especificaciones_pc 
            (dispositivo_id, cpu, ram, hd, sistema_operativo) 
            VALUES (?, ?, ?, ?, ?)
        """.trimIndent()

        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, especificacionesPc.dispositivoId)
            statement.setString(2, especificacionesPc.cpu)
            statement.setString(3, especificacionesPc.ram)
            statement.setString(4, especificacionesPc.hd)
            statement.setString(5, especificacionesPc.sistemaOperativo)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override suspend fun actualizar(especificacionesPc: EspecificacionPC): Boolean {
        val sql = """
            UPDATE especificaciones_pc SET 
            cpu = ?, ram = ?, hd = ?, sistema_operativo = ?
            WHERE dispositivo_id = ?
        """.trimIndent()

        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, especificacionesPc.cpu)
            statement.setString(2, especificacionesPc.ram)
            statement.setString(3, especificacionesPc.hd)
            statement.setString(4, especificacionesPc.sistemaOperativo)
            statement.setInt(5, especificacionesPc.dispositivoId)
            return statement.executeUpdate() > 0
        }
        return false
    }

    override suspend fun eliminar(idDispositivo: Int): Boolean {
        val sql = "DELETE FROM especificaciones_pc WHERE dispositivo_id = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, idDispositivo)
            return statement.executeUpdate() > 0
        }
        return false
    }
}

