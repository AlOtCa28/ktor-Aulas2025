package DAOs.EspecificacionPcDAO

import modelo.EspecificacionPC

interface EspecificacionesPcDAO {
    suspend fun getPorDispositivoId(idDispositivo: Int): EspecificacionPC?
    suspend fun insertar(especificacionesPc: EspecificacionPC): Boolean
    suspend fun actualizar(especificacionesPc: EspecificacionPC): Boolean
    suspend fun eliminar(idDispositivo: Int): Boolean
}