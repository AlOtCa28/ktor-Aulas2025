package DAOs

import parametros.Parametros
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object Database {
    private val url = "jdbc:mysql://"+Parametros.servidor+":" +  Parametros.puerto + "/" + Parametros.bbdd
    private val user = Parametros.usuario
    private val password = Parametros.passwd

    fun getConnection(): Connection? {
        return try {
            DriverManager.getConnection(url, user, password)
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }
}