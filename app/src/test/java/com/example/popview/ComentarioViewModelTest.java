package com.example.popview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.example.popview.data.Comentario
import com.example.popview.viewmodel.ComentarioViewModel;

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ComentarioViewModelTest {

    private lateinit var viewModel: ComentarioViewModel

    @Before
    fun setUp() {
        viewModel = ComentarioViewModel()
        viewModel.establecerUsuarioActual(1) // Usuario ficticio
    }

    @Test
    fun `validar texto vacío da error`() {
        viewModel.actualizarTextoComentario("")
        val estado = viewModel.estadoComentario.value

        assertEquals("El comentario no puede estar vacío", estado?.errorTexto)
        assertFalse(estado?.esValido ?: true)
    }

    @Test
    fun `validar puntuación inválida da error`() {
        viewModel.actualizarPuntuacion(5f)
        val estado = viewModel.estadoComentario.value

        assertEquals("La puntuación debe entre 0 y 4", estado?.errorPuntuacion)
        assertFalse(estado?.esValido ?: true)
    }

    @Test
    fun `enviar comentario válido lo genera correctamente`() {
        var comentarioEnviado: Comentario? = null

        viewModel.actualizarTextoComentario("Buena peli")
        viewModel.actualizarPuntuacion(3.5f)

        viewModel.enviarComentario(idTitulo = 1) {
            comentarioEnviado = it
        }

        assertNotNull(comentarioEnviado)
        assertEquals("Buena peli", comentarioEnviado?.comentaris)
        assertEquals(3.5f, comentarioEnviado?.rating)
        assertEquals(1, comentarioEnviado?.usuari_id)
        assertEquals(0, comentarioEnviado?.id) // Porque se autogenera después
    }

    @Test
    fun `editar comentario conserva el id y actualiza campos`() {
        var comentarioEditado: Comentario? = null

        viewModel.prepararEdicion(idComentario = 99, texto = "Antiguo", puntuacion = 1f)
        viewModel.actualizarTextoComentario("Actualizado")
        viewModel.actualizarPuntuacion(4f)

        viewModel.enviarComentario(idTitulo = 1) {
            comentarioEditado = it
        }

        assertNotNull(comentarioEditado)
        assertEquals(99, comentarioEditado?.id)
        assertEquals("Actualizado", comentarioEditado?.comentaris)
        assertEquals(4f, comentarioEditado?.rating)
    }

    @Test
    fun `agregar comentario se añade a la lista`() {
        val nuevo = Comentario(id = 1, usuari_id = 1, comentaris = "Test", rating = 3f)
        viewModel.agregarComentarioALista(nuevo)

        assertTrue(viewModel.comentarios.value!!.contains(nuevo))
    }

    @Test
    fun `editar comentario en lista lo reemplaza correctamente`() {
        val original = Comentario(id = 10, usuari_id = 1, comentaris = "Viejo", rating = 1f)
        val editado = Comentario(id = 10, usuari_id = 1, comentaris = "Nuevo", rating = 4f)

        viewModel.agregarComentarioALista(original)
        viewModel.editarComentarioEnLista(editado)

        val comentario = viewModel.comentarios.value!!.find { it.id == 10 }
        assertEquals("Nuevo", comentario?.comentaris)
        assertEquals(4f, comentario?.rating)
    }

    @Test
    fun `eliminar comentario lo quita solo a él`() {
        val comentario1 = Comentario(id = 1, usuari_id = 1, comentaris = "Primero", rating = 2f)
        val comentario2 = Comentario(id = 2, usuari_id = 1, comentaris = "Segundo", rating = 3f)

        viewModel.agregarComentarioALista(comentario1)
        viewModel.agregarComentarioALista(comentario2)

        viewModel.eliminarComentario(1)

        val lista = viewModel.comentarios.value!!
                assertFalse(lista.any { it.id == 1 })
        assertTrue(lista.any { it.id == 2 })
    }
}

