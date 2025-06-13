package DAOs.DispositivoDAO

import modelo.Dispositivo

interface DispositivoDAO {
    suspend fun listarDispositivos(): List<Dispositivo>
    suspend fun obtenerDispositivoPorId(id: Int): Dispositivo?
    suspend fun registrarDispositivo(dispositivo: Dispositivo): Boolean
    suspend fun actualizarDispositivo(dispositivo: Dispositivo): Boolean
    suspend fun eliminarDispositivo(id: Int): Boolean
}