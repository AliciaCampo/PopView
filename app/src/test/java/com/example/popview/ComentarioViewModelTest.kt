package com.example.popview;
import com.example.popview.viewmodel.ComentarioViewModel
import com.example.popview.data.Comentario
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ComentarioViewModelTest {

    private val viewModel = ComentarioViewModel()

    @Before
    fun setUp() {
        // Establecer un usuario ficticio para las pruebas
        viewModel.establecerUsuarioActual(1)
    }

    // Método para acceder al valor del LiveData de forma sincronizada
    private fun obtenerEstadoComentario() = viewModel.estadoComentario.value
    private fun obtenerComentarios() = viewModel.comentarios.value

    @Test
    fun `validar texto vacío da error`() {
        // Datos de entrada con comentario vacío
        viewModel.actualizarTextoComentario("")

        // Comprobar que el error de texto es el esperado
        val estado = obtenerEstadoComentario()
        assertEquals("El comentario no puede estar vacío", estado?.errorTexto)
        assertFalse(estado?.esValido ?: true)
    }

    @Test
    fun `validar puntuación inválida da error`() {
        // Datos de entrada con puntuación inválida
        viewModel.actualizarPuntuacion(5f)

        // Comprobar que el error de puntuación es el esperado
        val estado = obtenerEstadoComentario()
        assertEquals("La puntuación debe entre 0 y 4", estado?.errorPuntuacion)
        assertFalse(estado?.esValido ?: true)
    }

    @Test
    fun `enviar comentario válido lo genera correctamente`() {
        var comentarioEnviado: Comentario? = null

        // Datos válidos
        viewModel.actualizarTextoComentario("Buena peli")
        viewModel.actualizarPuntuacion(3.5f)

        // Enviar el comentario
        viewModel.enviarComentario(idTitulo = 1) {
            comentarioEnviado = it
        }

        // Comprobar que el comentario fue enviado correctamente
        assertNotNull(comentarioEnviado)
        assertEquals("Buena peli", comentarioEnviado?.comentaris)
        assertEquals(3.5f, comentarioEnviado?.rating)
        assertEquals(1, comentarioEnviado?.usuari_id)
        assertEquals(0, comentarioEnviado?.id) // Porque se autogenera después
    }

    @Test
    fun `editar comentario conserva el id y actualiza campos`() {
        var comentarioEditado: Comentario? = null

        // Preparar comentario para editar
        viewModel.prepararEdicion(idComentario = 99, texto = "Antiguo", puntuacion = 1f)
        viewModel.actualizarTextoComentario("Actualizado")
        viewModel.actualizarPuntuacion(4f)

        // Enviar el comentario editado
        viewModel.enviarComentario(idTitulo = 1) {
            comentarioEditado = it
        }

        // Comprobar que el comentario se editó correctamente
        assertNotNull(comentarioEditado)
        assertEquals(99, comentarioEditado?.id)
        assertEquals("Actualizado", comentarioEditado?.comentaris)
        assertEquals(4f, comentarioEditado?.rating)
    }

    @Test
    fun `agregar comentario se añade a la lista`() {
        val nuevo = Comentario(id = 1, usuari_id = 1, comentaris = "Test", rating = 3f)

        // Agregar el comentario a la lista
        viewModel.agregarComentarioALista(nuevo)

        // Comprobar que el comentario fue agregado correctamente
        val lista = obtenerComentarios()
        assertTrue(lista?.contains(nuevo) == true)
    }

    @Test
    fun `editar comentario en lista lo reemplaza correctamente`() {
        val original = Comentario(id = 10, usuari_id = 1, comentaris = "Viejo", rating = 1f)
        val editado = Comentario(id = 10, usuari_id = 1, comentaris = "Nuevo", rating = 4f)

        // Agregar y luego editar el comentario
        viewModel.agregarComentarioALista(original)
        viewModel.editarComentarioEnLista(editado)

        // Comprobar que el comentario fue editado correctamente
        val lista = obtenerComentarios()
        val comentario = lista?.find { it.id == 10 }
        assertEquals("Nuevo", comentario?.comentaris)
        assertEquals(4f, comentario?.rating)
    }

    @Test
    fun `eliminar comentario lo quita solo a él`() {
        val comentario1 = Comentario(id = 1, usuari_id = 1, comentaris = "Primero", rating = 2f)
        val comentario2 = Comentario(id = 2, usuari_id = 1, comentaris = "Segundo", rating = 3f)

        // Agregar comentarios a la lista
        viewModel.agregarComentarioALista(comentario1)
        viewModel.agregarComentarioALista(comentario2)

        // Eliminar el primer comentario
        viewModel.eliminarComentario(1)

        // Comprobar que solo el primer comentario fue eliminado
        val lista = obtenerComentarios()
        assertFalse(lista?.any { it.id == 1 } == true)
        assertTrue(lista?.any { it.id == 2 } == true)
    }
}