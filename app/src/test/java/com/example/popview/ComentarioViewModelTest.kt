package com.example.popview

import com.example.popview.viewmodel.ComentarioViewModel
import com.example.popview.data.Comentario
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class ComentarioViewModelTest {

    private lateinit var viewModel: ComentarioViewModel

    @Before
    fun setUp() {
        viewModel = ComentarioViewModel()
    }

    @Test
    fun `comentario con campos vacíos o inválidos retorna errores de validación`() {
        val comentarioInvalido = Comentario(
            usuari_id = 0,
            comentaris = "",
            rating = -1f
        )

        val estado = viewModel.validarComentario(comentarioInvalido)

        assertFalse(estado.esValido)
        assertEquals("El comentario no puede estar vacío", estado.errorComentario)
        assertEquals("El ID de usuario debe ser mayor que 0", estado.errorUsuarioId)
        assertEquals("La puntuación debe estar entre 0 y 4", estado.errorRating)
    }

    @Test
    fun `agregar comentario válido lo añade a la lista`() {
        val comentarioValido = Comentario(
            usuari_id = 1,
            comentaris = "Este es un comentario de prueba",
            rating = 4.5f
        )

        val resultado = viewModel.agregarComentario(comentarioValido)
        assertTrue(resultado)
        assertEquals(1, viewModel.obtenerTodos().size)
    }

    @Test
    fun `modificar comentario válido actualiza la lista`() {
        val comentarioValido = Comentario(
            id = 1,
            usuari_id = 1,
            comentaris = "Comentario original",
            rating = 3.0f
        )
        viewModel.agregarComentario(comentarioValido)

        val resultado = viewModel.modificarComentario(1, "Comentario modificado", 4.0f)
        assertTrue(resultado)
        assertEquals("Comentario modificado", viewModel.obtenerTodos()[0].comentaris)
        assertEquals(4.0f, viewModel.obtenerTodos()[0].rating)
    }

    @Test
    fun `eliminar comentario existente lo remueve de la lista`() {
        val comentarioValido = Comentario(
            id = 1,
            usuari_id = 1,
            comentaris = "Comentario a eliminar",
            rating = 3.5f
        )
        viewModel.agregarComentario(comentarioValido)

        val resultado = viewModel.eliminarComentario(1)
        assertTrue(resultado)
        assertTrue(viewModel.obtenerTodos().isEmpty())
    }

    @Test
    fun `inicialmente la lista de comentarios está vacía`() {
        assertTrue(viewModel.obtenerTodos().isEmpty())
    }

    @Test
    fun `comentario válido no tiene errores de validación`() {
        val comentarioValido = Comentario(
            usuari_id = 1,
            comentaris = "Comentario válido",
            rating = 4.0f
        )

        val estado = viewModel.validarComentario(comentarioValido)

        assertTrue(estado.esValido)
        assertEquals(null, estado.errorComentario)
        assertEquals(null, estado.errorUsuarioId)
        assertEquals(null, estado.errorRating)
    }

    @Test
    fun `comentario con comentaris de longitud máxima permitida es válido`() {
        val comentarioValido = Comentario(
            usuari_id = 1,
            comentaris = "a".repeat(500), // Asumiendo que 500 es la longitud máxima permitida
            rating = 3.0f
        )

        val estado = viewModel.validarComentario(comentarioValido)

        assertTrue(estado.esValido)
        assertEquals(null, estado.errorComentario)
    }

    @Test
    fun `agregar comentario con comentaris vacío retorna error`() {
        val comentarioInvalido = Comentario(
            usuari_id = 1,
            comentaris = "",
            rating = 3.0f
        )

        val estado = viewModel.validarComentario(comentarioInvalido)

        assertFalse(estado.esValido)
        assertEquals("El comentario no puede estar vacío", estado.errorComentario)
    }

    @Test
    fun `agregar múltiples comentarios válidos incrementa el tamaño de la lista`() {
        val comentario1 = Comentario(
            usuari_id = 1,
            comentaris = "Primer comentario",
            rating = 4.0f
        )
        val comentario2 = Comentario(
            usuari_id = 2,
            comentaris = "Segundo comentario",
            rating = 3.5f
        )

        viewModel.agregarComentario(comentario1)
        viewModel.agregarComentario(comentario2)

        assertEquals(2, viewModel.obtenerTodos().size)
    }
}
