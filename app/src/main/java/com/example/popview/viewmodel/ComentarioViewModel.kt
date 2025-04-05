package com.example.popview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popview.data.Comentario

data class EstadoComentario(
    var esValido: Boolean = false,
    var errorTexto: String? = null,
    var errorPuntuacion: String? = null
)

class ComentarioViewModel : ViewModel() {

    private var _textoComentario: String = ""
    private var _puntuacion: Float = 0f
    private var _idComentario: Int? = null // Para editar comentarios existentes

    private val _estadoComentario = MutableLiveData<EstadoComentario>()
    val estadoComentario: LiveData<EstadoComentario> = _estadoComentario

    // Lista de comentarios en memoria
    private val _comentarios = MutableLiveData<MutableList<Comentario>>(mutableListOf())
    val comentarios: LiveData<MutableList<Comentario>> = _comentarios

    private var idUsuarioActual: Int = -1

    fun establecerUsuarioActual(id: Int) {
        idUsuarioActual = id
    }

    fun actualizarTextoComentario(texto: String) {
        _textoComentario = texto
        validarTexto()
    }

    fun actualizarPuntuacion(nuevaPuntuacion: Float) {
        _puntuacion = nuevaPuntuacion
        validarPuntuacion()
    }

    fun prepararEdicion(idComentario: Int, texto: String, puntuacion: Float) {
        _idComentario = idComentario
        _textoComentario = texto
        _puntuacion = puntuacion
    }

    private fun validarTexto() {
        val estado = _estadoComentario.value ?: EstadoComentario()
        estado.errorTexto = if (_textoComentario.isBlank()) "El comentari no pot estar buit" else null
        _estadoComentario.value = estado
        actualizarValidacionGeneral()
    }

    private fun validarPuntuacion() {
        val estado = _estadoComentario.value ?: EstadoComentario()
        estado.errorPuntuacion = if (_puntuacion < 0f  || _puntuacion > 4f) "La puntuació ha de ser entre 0 i 4" else null
        _estadoComentario.value = estado
        actualizarValidacionGeneral()
    }

    private fun actualizarValidacionGeneral() {
        val estado = _estadoComentario.value ?: EstadoComentario()
        estado.esValido = estado.errorTexto == null && estado.errorPuntuacion == null
        _estadoComentario.value = estado
    }

    fun enviarComentario(idTitulo: Int, onEnviar: (Comentario) -> Unit) {
        validarTexto()
        validarPuntuacion()
        val estado = _estadoComentario.value
        if (estado?.esValido == true) {
            val comentario = if (_idComentario != null) {
                // Es una edición, sí pasamos el ID
                Comentario(
                    id = _idComentario!!,
                    usuari_id = idUsuarioActual,
                    comentaris = _textoComentario,
                    rating = _puntuacion
                )
            } else {
                // Es nuevo, dejamos que el sistema asigne el ID
                Comentario(
                    id = 0, // o puedes usar null si tu clase lo permite
                    usuari_id = idUsuarioActual,
                    comentaris = _textoComentario,
                    rating = _puntuacion
                )
            }

            onEnviar(comentario)
            _idComentario = null
        }
    }

    fun agregarComentarioALista(nuevoComentario: Comentario) {
        _comentarios.value?.add(nuevoComentario)
        _comentarios.value = _comentarios.value // Notificar cambio
    }

    fun editarComentarioEnLista(comentarioEditado: Comentario) {
        val index = _comentarios.value?.indexOfFirst { it.id == comentarioEditado.id }
        if (index != null && index != -1) {
            _comentarios.value?.set(index, comentarioEditado)
            _comentarios.value = _comentarios.value
        }
    }

    fun eliminarComentario(idComentario: Int) {
        _comentarios.value = _comentarios.value?.filter { it.id != idComentario }?.toMutableList()
    }

    fun limpiarFormulario() {
        _textoComentario = ""
        _puntuacion = 0f
        _idComentario = null
        _estadoComentario.value = EstadoComentario()
    }
}
