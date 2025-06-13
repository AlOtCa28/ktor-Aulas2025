package DAOs.EspecificacionMonitorDAO

import modelo.EspecificacionMonitor

interface EspecificacionesMonitorDAO {
    suspend fun getPorDispositivoId(idDispositivo: Int): EspecificacionMonitor?
    suspend fun insertar(especificacionesMonitor: EspecificacionMonitor): Boolean
    suspend fun actualizar(especificacionesMonitor: EspecificacionMonitor): Boolean
    suspend fun eliminar(idDispositivo: Int): Boolean
}