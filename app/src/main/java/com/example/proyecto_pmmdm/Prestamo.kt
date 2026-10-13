package com.example.proyecto_pmmdm

/** Un objeto prestado: qué es, a quién, desde cuándo y notas opcionales. */
data class Prestamo(
    val id: Int,
    val objeto: String,
    val persona: String,
    val fecha: String,
    val notas: String
)

/** Claves de los extras de los Intent (un único sitio para no equivocarse). */
object Claves {
    const val ID = "id"
    const val OBJETO = "objeto"
    const val PERSONA = "persona"
    const val FECHA = "fecha"
    const val NOTAS = "notas"
}
