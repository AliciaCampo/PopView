package com.example.popview.viewmodel

import androidx.lifecycle.ViewModel
import com.example.popview.data.Comentario

data class EstadoComentario(
    var esValido: Boolean,
    var errorComentario: String? = null,
    var errorUsuarioId: String? = null,
    var errorRating: String? = null
)

class ComentarioViewModel : ViewModel() {

    private val listaComentarios = mutableListOf<Comentario>()

    fun validarComentario(comentario: Comentario): EstadoComentario {
        val estado = EstadoComentario(esValido = true)
        if (comentario.comentaris.isBlank()) {
            estado.esValido = false
            estado.errorComentario = "El comentario no puede estar vacío"
        }
        if (comentario.usuari_id <= 0) {
            estado.esValido = false
            estado.errorUsuarioId = "El ID de usuario debe ser mayor que 0"
        }
        if (comentario.rating < 0 || comentario.rating > 5) {
            estado.esValido = false
            estado.errorRating = "La puntuación debe estar entre 0 y 5"
        }
        return estado
    }

    fun agregarComentario(comentario: Comentario): Boolean {
        val estado = validarComentario(comentario)
        return if (estado.esValido) {
            listaComentarios.add(comentario)
            true
        } else false
    }

    fun modificarComentario(id: Int, nuevoComentario: String, nuevoRating: Float): Boolean {
        val comentario = listaComentarios.find { it.id == id } ?: return false
        val nuevoComentarioObj = comentario.copy(comentaris = nuevoComentario, rating = nuevoRating)
        val estado = validarComentario(nuevoComentarioObj)
        return if (estado.esValido) {
            listaComentarios[listaComentarios.indexOf(comentario)] = nuevoComentarioObj
            true
        } else false
    }

    fun eliminarComentario(id: Int): Boolean {
        return listaComentarios.removeIf { it.id == id }
    }

    fun obtenerTodos(): List<Comentario> = listaComentarios
}
