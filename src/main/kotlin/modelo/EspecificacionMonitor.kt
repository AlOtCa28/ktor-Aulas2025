package modelo

import kotlinx.serialization.Serializable

@Serializable
data class EspecificacionMonitor(
    val dispositivoId: Int,
    val resolucionMaxima: String?,
    val pulgadas: Double?,
    val orientable: Boolean?
)
