package com.example.popview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.popview.viewmodel.UsuarioViewModel
import org.junit.Assert.*
import androidx.lifecycle.Observer
import org.junit.Rule
import org.junit.Test

class UsuarioViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private fun getViewModel() = UsuarioViewModel()

    // 1. Test general (todo valido)
    @Test
    fun `crear usuario con datos válidos`() {
        val vm = getViewModel()
        vm.validarDatosUsuario("Alicia", "alicia@gmail.com", 22, "Contrasenya@123", "avataruser1.png")

        val estado = vm.validacionDatos.value
        assertTrue(estado!!.esValido)
    }

    // 2. Test nombre válido
    @Test
    fun `nombre válido`() {
        val vm = getViewModel()
        vm.validarDatosUsuario("Alicia", null, null, null)

        val estado = vm.validacionDatos.value
        assertTrue(estado!!.errorNombreUsuario == null)
    }

    // 3. Test nombre inválido (corto)
    @Test
    fun `nombre inválido por ser corto`() {
        val vm = getViewModel()
        vm.validarDatosUsuario("Ali", null, null, null)

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
        assertEquals("El nom ha de tenir mínim 5 caràcters", estado.errorNombreUsuario)
    }

    // 4. Test email válido
    @Test
    fun `email válido`() {
        val vm = getViewModel()
        vm.validarDatosUsuario(null, "user@test.com", null, null)

        val estado = vm.validacionDatos.value
        assertTrue(estado!!.errorEmailUsuario == null)
    }

    // 5. Test email inválido
    @Test
    fun `email inválido`() {
        val vm = getViewModel()
        vm.validarDatosUsuario(null, "user@mal", null, null)

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
        assertEquals("L'email no és vàlid", estado.errorEmailUsuario)
    }

    // 6. Test edad válida
    @Test
    fun `edad válida`() {
        val vm = getViewModel()
        vm.validarDatosUsuario(null, null, 20, null)

        val estado = vm.validacionDatos.value
        assertTrue(estado!!.errorEdadUsuario == null)
    }

    // 7. Test edad inválida
    @Test
    fun `edad inválida`() {
        val vm = getViewModel()
        vm.validarDatosUsuario(null, null, 0, null)

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
        assertEquals("L'edat ha de ser major que 0", estado.errorEdadUsuario)
    }

    // 8. Test contraseña válida
    @Test
    fun `contraseña válida`() {
        val vm = getViewModel()
        vm.validarDatosUsuario(null, null, null, "Pass@1234")

        val estado = vm.validacionDatos.value
        assertTrue(estado!!.errorContrasenyaUsuario == null)
    }

    // 9. Test contraseña inválida
    @Test
    fun `contraseña inválida`() {
        val vm = getViewModel()
        vm.validarDatosUsuario(null, null, null, "abc")

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
        assertEquals("La contrasenya ha de tenir mínim 8 caràcters, un número i un símbol especial", estado.errorContrasenyaUsuario)
    }

    // 10. Test avatar válido
    @Test
    fun `avatar válido`() {
        val vm = getViewModel()
        val observer = Observer<Boolean> {}
        vm.avatarValido.observeForever(observer)

        vm.validarDatosUsuario(
            nombre = "Nombre",
            email = "email@valido.com",
            edad = 25,
            contrasenya = "Contrasenya@123",
            avatar = "avataruser1.png"  // Nombre del archivo en drawable
        )

        assertTrue(vm.avatarValido.value == true)

        vm.avatarValido.removeObserver(observer)
    }
    // 11. Test avatar inválido
    @Test
    fun `avatar inválido`() {
        val vm = getViewModel()
        vm.validarDatosUsuario(null, null, null, null, "imagenloca.png")

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
    }
}
