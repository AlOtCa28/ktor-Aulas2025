package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Aula(
    val id: Int? = null,
    val nombre: String,
    val idEncargado: Int? // Puede ser null si no tiene encargado
)

