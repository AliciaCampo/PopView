package com.example.popview

import com.example.popview.data.Titulo
import com.example.popview.viewmodel.TituloViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TituloViewModelTest {

    private lateinit var viewModel: TituloViewModel

    @Before
    fun setup() {
        viewModel = TituloViewModel()
    }

    @Test
    fun `titulo con campos vacíos retorna errores de validación`() {
        val tituloInvalido = Titulo(
            imagen = "",
            nombre = "",
            description = "",
            genero = "",
            edadRecomendada = -1,
            platforms = "",
            rating = -1f,
            comments = null,
            id = 1
        )

        val estado = viewModel.validarTitulo(tituloInvalido)

        assertFalse(estado.esValido)
        assertEquals("El nom no pot estar buit", estado.errorNombre)
        assertEquals("Cal afegir una imatge", estado.errorImagen)
        assertEquals("Cal afegir una descripció", estado.errorDescripcion)
        assertEquals("Cal indicar un gènere", estado.errorGenero)
        assertEquals("L'edat recomanada ha de ser major que 0", estado.errorEdad)
        assertEquals("Cal indicar almenys una plataforma", estado.errorPlataforma)
        assertEquals("La puntuació ha d'estar entre 0 i 5", estado.errorRating)
    }

    @Test
    fun `titulo válido se añade correctamente a la lista`() {
        val tituloValido = Titulo(
            imagen = "https://exemple.com/imatge.jpg",
            nombre = "Títol de Prova",
            description = "Descripció",
            genero = "Acció",
            edadRecomendada = 12,
            platforms = "Netflix",
            rating = 4.0f,
            comments = emptyList(),
            id = 1
        )

        val resultado = viewModel.agregarTitulo(tituloValido)

        assertTrue(resultado)
        assertEquals(1, viewModel.obtenerTodos().size)
    }

    @Test
    fun `rating fuera de rango negativo da error`() {
        val titulo = Titulo(
            imagen = "url.jpg",
            nombre = "Títol",
            description = "desc",
            genero = "Drama",
            edadRecomendada = 12,
            platforms = "HBO",
            rating = -1f,
            comments = null,
            id = 3
        )

        val estado = viewModel.validarTitulo(titulo)

        assertFalse(estado.esValido)
        assertEquals("La puntuació ha d'estar entre 0 i 5", estado.errorRating)
    }

    @Test
    fun `rating mayor a 5 da error`() {
        val titulo = Titulo(
            imagen = "url.jpg",
            nombre = "Títol",
            description = "desc",
            genero = "Drama",
            edadRecomendada = 12,
            platforms = "HBO",
            rating = 6f,
            comments = null,
            id = 3
        )

        val estado = viewModel.validarTitulo(titulo)

        assertFalse(estado.esValido)
        assertEquals("La puntuació ha d'estar entre 0 i 5", estado.errorRating)
    }

    @Test
    fun `filtrar por género retorna solo los del género indicado`() {
        val accion = Titulo(
            imagen = "img1.jpg",
            nombre = "Acció 1",
            description = "desc",
            genero = "Acció",
            edadRecomendada = 16,
            platforms = "Netflix",
            rating = 4.0f,
            comments = emptyList(),
            id = 1
        )

        val comedia = Titulo(
            imagen = "img2.jpg",
            nombre = "Comèdia 1",
            description = "desc",
            genero = "Comèdia",
            edadRecomendada = 10,
            platforms = "Amazon",
            rating = 3.0f,
            comments = emptyList(),
            id = 2
        )

        viewModel.agregarTitulo(accion)
        viewModel.agregarTitulo(comedia)

        val resultado = viewModel.filtrarPorGenero("Acció")

        assertEquals(1, resultado.size)
        assertEquals("Acció 1", resultado[0].nombre)
    }

    @Test
    fun `lista de títulos vacía inicialmente`() {
        assertTrue(viewModel.obtenerTodos().isEmpty())
    }

    @Test
    fun `validar titulo correcto no da errores`() {
        val titulo = Titulo(
            imagen = "https://exemple.com/imatge.jpg",
            nombre = "Títol Complet",
            description = "Descripció completa",
            genero = "Drama",
            edadRecomendada = 10,
            platforms = "Netflix",
            rating = 4.0f,
            comments = emptyList(),
            id = 5
        )

        val estado = viewModel.validarTitulo(titulo)

        assertTrue(estado.esValido)
        assertNull(estado.errorNombre)
        assertNull(estado.errorImagen)
        assertNull(estado.errorDescripcion)
        assertNull(estado.errorGenero)
        assertNull(estado.errorEdad)
        assertNull(estado.errorPlataforma)
        assertNull(estado.errorRating)
    }
}
