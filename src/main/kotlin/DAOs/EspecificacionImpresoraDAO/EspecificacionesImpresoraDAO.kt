package DAOs.EspecificacionImpresoraDAO

import modelo.EspecificacionImpresora

interface EspecificacionesImpresoraDAO {
    suspend fun getPorDispositivoId(idDispositivo: Int): EspecificacionImpresora?
    suspend fun insertar(especificacionesImpresora: EspecificacionImpresora): Boolean
    suspend fun actualizar(especificacionesImpresora: EspecificacionImpresora): Boolean
    suspend fun eliminar(idDispositivo: Int): Boolean
}