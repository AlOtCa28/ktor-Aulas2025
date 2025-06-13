package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Mensaje(
    val id: Long,
    val contenido: String?,
    val fecha: String,
    val mostrado: Boolean
)
