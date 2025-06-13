package modelo

import kotlinx.serialization.Serializable

@Serializable
data class EspecificacionImpresora(
    val dispositivoId: Int,
    val tipo: String?
)
