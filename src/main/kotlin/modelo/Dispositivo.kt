package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Dispositivo(
    val id: Int? = null,
    val codigo: String,
    val descripcion: String,
    val estado: String,
    val marca: String,
    val modelo: String,
    val numeroSerie: String,
    val ubicacionReferencia: String,
    val ubicacionActual: String,
    val aulaId: Int?,  // Cambiado a nullable
    val tipo: String
)
