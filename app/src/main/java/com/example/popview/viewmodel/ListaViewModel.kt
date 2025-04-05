package com.example.popview.viewmodel

import androidx.lifecycle.ViewModel
import com.example.popview.data.Titulo

data class EstadoLista(
    var esValido: Boolean,
    var errorTitulo: String? = null,
    var errorPrivada: String? = null,
    var errorUsuari: String? = null,
    var errorTitulos: MutableList<Titulo> = mutableListOf()
)

class ListaViewModel : ViewModel() {

    private var _titulo: String = ""
    private var _esPrivada: Boolean? = null
    private var _usuario: String? = null
    private val _titulos: MutableList<Titulo> = mutableListOf()

    fun actualizarTitulo(nuevoTitulo: String) {
        _titulo = nuevoTitulo
    }

    fun actualizarPrivada(valor: Boolean?) {
        _esPrivada = valor
    }

    fun actualizarUsuario(usuario: String?) {
        _usuario = usuario
    }

    fun agregarTitulo(titulo: Titulo): Boolean {
        if (titulo.nombre.isBlank()) {
            return false
        }
        _titulos.add(titulo)
        return true
    }

    fun eliminarTitulo(id: Int): Boolean {
        return _titulos.removeIf { it.id == id }
    }

    fun obtenerTitulos(): List<Titulo> = _titulos

    fun validarLista(): EstadoLista {
        val estado = EstadoLista(esValido = true)

        if (_titulo.isBlank()) {
            estado.esValido = false
            estado.errorTitulo = "El títol és obligatori"
        }

        if (_esPrivada == null) {
            estado.esValido = false
            estado.errorPrivada = "Has d'indicar si la llista és privada o pública"
        }

        if (_usuario.isNullOrBlank()) {
            estado.esValido = false
            estado.errorUsuari = "El usuari és obligatori"
        }

        if (_titulos.isEmpty()) {
            estado.esValido = false
            estado.errorTitulos = mutableListOf()
        }

        return estado
    }
}
