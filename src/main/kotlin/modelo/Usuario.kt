package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val id: Int? = null,
    val nombre: String,
    val email: String,
    val passwordHash: String,
    val rol: Int // 1 = jefe, 2 = encargado, 3 = profesor
)
