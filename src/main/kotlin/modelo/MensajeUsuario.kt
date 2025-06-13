package modelo

import kotlinx.serialization.Serializable

data class MensajeUsuario(
    val mensajeId: Long,
    val usuarioCorreo: String,
    val mostrado: Boolean = false
)
