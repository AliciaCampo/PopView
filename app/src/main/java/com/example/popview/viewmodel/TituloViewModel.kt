package com.example.popview.viewmodel

import androidx.lifecycle.ViewModel
import com.example.popview.data.Titulo

data class EstadoTitulo(
    var esValido: Boolean,
    var errorNombre: String? = null,
    var errorImagen: String? = null,
    var errorDescripcion: String? = null,
    var errorGenero: String? = null,
    var errorEdad: String? = null,
    var errorPlataforma: String? = null,
    var errorRating: String? = null
)

class TituloViewModel : ViewModel() {

    private val listaTitulos = mutableListOf<Titulo>()

    fun validarTitulo(titulo: Titulo): EstadoTitulo {
        val estado = EstadoTitulo(esValido = true)

        if (titulo.nombre.isBlank()) {
            estado.esValido = false
            estado.errorNombre = "El nom no pot estar buit"
        }

        if (titulo.imagen.isBlank()) {
            estado.esValido = false
            estado.errorImagen = "Cal afegir una imatge"
        }

        if (titulo.description.isBlank()) {
            estado.esValido = false
            estado.errorDescripcion = "Cal afegir una descripció"
        }

        if (titulo.genero.isNullOrBlank()) {
            estado.esValido = false
            estado.errorGenero = "Cal indicar un gènere"
        }


        if (titulo.edadRecomendada == null || titulo.edadRecomendada <= 0) {
            estado.esValido = false
            estado.errorEdad = "L'edat recomanada ha de ser major que 0"
        }


        if (titulo.platforms.isBlank()) {
            estado.esValido = false
            estado.errorPlataforma = "Cal indicar almenys una plataforma"
        }

        if (titulo.rating < 0 || titulo.rating > 5) {
            estado.esValido = false
            estado.errorRating = "La puntuació ha d'estar entre 0 i 5"
        }

        return estado
    }

    fun agregarTitulo(titulo: Titulo): Boolean {
        val estado = validarTitulo(titulo)
        return if (estado.esValido) {
            listaTitulos.add(titulo)
            true
        } else false
    }

    fun eliminarTitulo(id: Int): Boolean {
        return listaTitulos.removeIf { it.id == id }
    }

    fun filtrarPorGenero(genero: String): List<Titulo> {
        return listaTitulos.filter { it.genero == genero }
    }

    fun obtenerTodos(): List<Titulo> = listaTitulos
}
