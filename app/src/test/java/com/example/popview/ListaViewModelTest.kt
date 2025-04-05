package com.example.popview

import com.example.popview.data.Titulo
import com.example.popview.viewmodel.ListaViewModel
import org.junit.Assert.*
import org.junit.Test

class ListaViewModelTest {

    private val viewModel = ListaViewModel()

    @Test
    fun `actualizarTitulo cambia correctamente el título de la lista`() {
        val nuevoTitulo = "Nuevo Títol"
        viewModel.actualizarTitulo(nuevoTitulo)
        assertEquals(nuevoTitulo, viewModel._titulo)
    }

    @Test
    fun `actualizarPrivada cambia correctamente el estado de la privacidad`() {
        viewModel.actualizarPrivada(true)
        assertTrue(viewModel._esPrivada == true)
    }

    @Test
    fun `agregarTitulo con título válido agrega el título a la lista`() {
        val tituloValido = Titulo(
            imagen = "https://exemple.com/imatge.jpg",
            nombre = "Títol Correcte",
            description = "Descripció de títol correcte",
            genero = "Acció",
            edadRecomendada = 10,
            platforms = "Netflix",
            rating = 4.0f,
            comments = emptyList(),
            id = 1
        )
        val resultado = viewModel.agregarTitulo(tituloValido)
        assertTrue(resultado)
        assertEquals(1, viewModel.obtenerTitulos().size)

        val titulos = viewModel.obtenerTitulos()
        assertTrue(titulos.contains(tituloValido))
    }

    @Test
    fun `eliminarTitulo con ID válido elimina el título de la lista`() {
        val tituloValido = Titulo(
            imagen = "https://exemple.com/imatge.jpg",
            nombre = "Títol a eliminar",
            description = "Descripció de títol a eliminar",
            genero = "Acció",
            edadRecomendada = 10,
            platforms = "Netflix",
            rating = 4.0f,
            comments = emptyList(),
            id = 1
        )
        viewModel.agregarTitulo(tituloValido)
        val resultado = viewModel.eliminarTitulo(1)
        assertTrue(resultado)
        assertEquals(0, viewModel.obtenerTitulos().size)
    }

    @Test
    fun `validarLista retorna estado inválido cuando hay errores`() {
        viewModel.actualizarTitulo("")
        viewModel.actualizarPrivada(null)
        val estado = viewModel.validarLista()
        assertFalse(estado.esValido)
        assertEquals("El títol és obligatori", estado.errorTitulo)
        assertEquals("Has d'indicar si la llista és privada o pública", estado.errorPrivada)
        assertNotNull(estado.errorTitulos)
    }

    @Test
    fun `validarLista retorna estado válido cuando no hay errores`() {
        viewModel.actualizarTitulo("Títol Correcte")
        viewModel.actualizarPrivada(true)

        val tituloValido = Titulo(
            imagen = "https://exemple.com/imatge.jpg",
            nombre = "Títol Correcte",
            description = "Descripció de títol correcte",
            genero = "Acció",
            edadRecomendada = 10,
            platforms = "Netflix",
            rating = 4.0f,
            comments = emptyList(),
            id = 1
        )
        viewModel.agregarTitulo(tituloValido)

        val estado = viewModel.validarLista()

        assertTrue(estado.esValido)
        assertNull(estado.errorTitulo)
        assertNull(estado.errorPrivada)
    }

    @Test
    fun `agregarTitulo con ID duplicado no agrega el título a la lista`() {
        val tituloValido = Titulo(
            imagen = "https://exemple.com/imatge.jpg",
            nombre = "Títol Correcte",
            description = "Descripció de títol correcte",
            genero = "Acció",
            edadRecomendada = 10,
            platforms = "Netflix",
            rating = 4.0f,
            comments = emptyList(),
            id = 1
        )
        viewModel.agregarTitulo(tituloValido)
        val resultado = viewModel.agregarTitulo(tituloValido)
        assertFalse(resultado)
    }
}
