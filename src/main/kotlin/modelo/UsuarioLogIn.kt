package modelo.USUARIO

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioLogIn(val nombre:String
                        ,val contraseña:String)
