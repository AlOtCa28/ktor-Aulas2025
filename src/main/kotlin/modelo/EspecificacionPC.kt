package modelo

import kotlinx.serialization.Serializable

@Serializable
data class EspecificacionPC(
    val dispositivoId: Int,
    val cpu: String?,
    val ram: String?,
    val hd: String?,
    val sistemaOperativo: String?
)
