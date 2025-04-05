package com.example.popview

import com.example.popview.data.Titulo
import com.example.popview.viewmodel.TituloViewModel
import org.junit.Assert.*
import org.junit.Test

class TituloViewModelTest {

    private val viewModel = TituloViewModel()

    @Test
    fun `titulo con campos vacíos retorna errores de validación`() {
        val tituloInvalido = Titulo(
            imagen = "",
            nombre = "",
            description = "Descripció de prova",
            genero = "Aventura",
            edadRecomendada = 12,
            platforms = "",
            rating = -1f,
            comments = null,
            id = 1
        )

        val estado = viewModel.validarTitulo(tituloInvalido)

        assertFalse(estado.esValido)
        assertEquals("El nom no pot estar buit", estado.errorNombre)
        assertEquals("Cal afegir una imatge", estado.errorImagen)
        assertEquals("Cal indicar almenys una plataforma", estado.errorPlataforma)
        assertEquals("La puntuació ha d'estar entre 0 i 5", estado.errorRating)
    }
    @Test
    fun `agregar titulo válido lo añade a la lista`() {
        val tituloValido = Titulo(
            imagen = "https://exemple.com/imatge.jpg",
            nombre = "Títol de Prova",
            description = "Descripció",
            genero = "Acció",
            edadRecomendada = 16,
            platforms = "Netflix",
            rating = 4.5f,
            comments = emptyList(),
            id = 2
        )

        val resultado = viewModel.agregarTitulo(tituloValido)
        assertTrue(resultado)
        assertEquals(1, viewModel.obtenerTodos().size)
    }
    @Test
    fun `rating menor que 0 retorna error`() {
        val tituloInvalido = Titulo(
            imagen = "https://exemple.com/imatge.jpg",
            nombre = "Títol amb rating incorrecte",
            description = "Descripció",
            genero = "Acció",
            edadRecomendada = 16,
            platforms = "Netflix",
            rating = -1f,  // Rating fuera del rango
            comments = null,
            id = 1
        )

        val estado = viewModel.validarTitulo(tituloInvalido)

        // Verificar que el estado no es válido y que se muestra el error correspondiente
        assertFalse(estado.esValido)
        assertEquals("La puntuació ha d'estar entre 0 i 5", estado.errorRating)
    }
    @Test
    fun `rating mayor que 5 retorna error`() {
        val tituloInvalido = Titulo(
            imagen = "https://exemple.com/imatge.jpg",
            nombre = "Títol amb rating incorrecte",
            description = "Descripció",
            genero = "Acció",
            edadRecomendada = 16,
            platforms = "Netflix",
            rating = 6f,  // Rating fuera del rango
            comments = null,
            id = 1
        )

        val estado = viewModel.validarTitulo(tituloInvalido)

        // Verificar que el estado no es válido y que se muestra el error correspondiente
        assertFalse(estado.esValido)
        assertEquals("La puntuació ha d'estar entre 0 i 5", estado.errorRating)
    }
    @Test
    fun `filtrar títulos por género funciona correctamente`() {
        val tituloAccion = Titulo(
            imagen = "https://exemple.com/imatge_accio.jpg",
            nombre = "Títol Acció",
            description = "Descripció Acció",
            genero = "Acció",
            edadRecomendada = 16,
            platforms = "Netflix",
            rating = 4.5f,
            comments = emptyList(),
            id = 1
        )
        val tituloComedia = Titulo(
            imagen = "https://exemple.com/imatge_comedia.jpg",
            nombre = "Títol Comèdia",
            description = "Descripció Comèdia",
            genero = "Comèdia",
            edadRecomendada = 12,
            platforms = "Amazon Prime",
            rating = 3.8f,
            comments = emptyList(),
            id = 2
        )

        viewModel.agregarTitulo(tituloAccion)
        viewModel.agregarTitulo(tituloComedia)

        val titulosAccion = viewModel.filtrarPorGenero("Acció")

        // Verificar que solo se devuelve el título de género "Acció"
        assertEquals(1, titulosAccion.size)
        assertEquals("Títol Acció", titulosAccion[0].nombre)
    }
    @Test
    fun `inicialmente la lista de títulos está vacía`() {
        assertTrue(viewModel.obtenerTodos().isEmpty())
    }
    @Test
    fun `titulo válido no tiene errores de validación`() {
        val tituloValido = Titulo(
            imagen = "https://exemple.com/imatge.jpg",
            nombre = "Títol Correcte",
            description = "Descripció de títol correcte",
            genero = "Comèdia",
            edadRecomendada = 10,
            platforms = "Netflix",
            rating = 4.0f,
            comments = emptyList(),
            id = 1
        )

        val estado = viewModel.validarTitulo(tituloValido)

        // Verificar que el estado es válido y no hay errores
        assertTrue(estado.esValido)
        assertNull(estado.errorNombre)
        assertNull(estado.errorImagen)
        assertNull(estado.errorPlataforma)
        assertNull(estado.errorRating)
    }
}