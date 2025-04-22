package DAOs.AulasDAO

import modelo.Aula

interface AulaDAO {
    fun obtenerAulaPorId(id: Int): Aula?
    fun obtenerAulaPorNombre(nombre: String): Aula?
    fun registrarAula(aula: Aula): Boolean
    fun actualizarAula(aula: Aula): Boolean
    fun eliminarAula(id: Int): Boolean
    fun listarAulas(): List<Aula>
}