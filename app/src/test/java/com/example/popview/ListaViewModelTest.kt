package com.example.popview

import com.example.popview.viewmodel.ListaViewModel
import com.example.popview.viewmodel.EstadoLista
import org.junit.Assert.*
import org.junit.Test

class ListaViewModelTest {

    @Test
    fun `validarLista con datos válidos`() {
        val viewModel = ListaViewModel()

        viewModel.actualizarTitulo("Título de la lista")
        viewModel.actualizarPrivada(true)
        viewModel.actualizarUsuario("Usuario")

        val estado: EstadoLista = viewModel.validarLista()

        assertTrue("La lista debe ser válida", estado.esValido)

        assertNull("El error de título no debe estar presente", estado.errorTitulo)
        assertNull("El error de privacidad no debe estar presente", estado.errorPrivada)
        assertNull("El error de usuario no debe estar presente", estado.errorUsuari)
        assertNull("El error de títulos no debe estar presente", estado.errorTitulos)
    }

    @Test
    fun `validarLista con título vacío`() {
        val viewModel = ListaViewModel()

        viewModel.actualizarTitulo("")
        viewModel.actualizarPrivada(true)
        viewModel.actualizarUsuario("Usuario")

        val estado: EstadoLista = viewModel.validarLista()

        assertFalse("La lista no debe ser válida", estado.esValido)
        assertNotNull("El error de título debe estar presente", estado.errorTitulo)
    }

    @Test
    fun `validarLista con lista sin títulos`() {
        val viewModel = ListaViewModel()

        viewModel.actualizarTitulo("Título de la lista")
        viewModel.actualizarPrivada(true)
        viewModel.actualizarUsuario("Usuario")

        val estado: EstadoLista = viewModel.validarLista()

        assertFalse("La lista no debe ser válida", estado.esValido)
        assertNotNull("El error de títulos debe estar presente", estado.errorTitulos)
    }
}
