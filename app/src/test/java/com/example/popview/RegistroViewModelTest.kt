package com.example.popview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.popview.viewmodel.RegistroViewModel
import org.junit.Assert.*
import androidx.lifecycle.Observer
import org.junit.Rule
import org.junit.Test

class RegistroViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private fun getViewModel() = RegistroViewModel()

    // 1. Test general (todo válido)
    @Test
    fun `registro con datos válidos`() {
        val vm = getViewModel()
        val resultado = vm.validarDatosRegistro(
            "Alicia",
            "alicia@gmail.com",
            "22",
            "Contrasenya@123",
            "Contrasenya@123",
            "avataruser1.png"
        )

        assertTrue(resultado)
        val estado = vm.validacionDatos.value
        assertTrue(estado!!.esValido)
        assertNull(estado.errorNombreUsuario)
        assertNull(estado.errorEmailUsuario)
        assertNull(estado.errorEdadUsuario)
        assertNull(estado.errorContrasenyaUsuario)
        assertNull(estado.errorConfirmacionContrasenya)
        assertNull(estado.errorAvatar)
    }

    // 2. Test nombre válido
    @Test
    fun `nombre válido`() {
        val vm = getViewModel()
        vm.validarDatosRegistro("Alicia", null, null, null, null)

        val estado = vm.validacionDatos.value
        assertNull(estado!!.errorNombreUsuario)
    }

    // 3. Test nombre inválido (corto)
    @Test
    fun `nombre inválido por ser corto`() {
        val vm = getViewModel()
        vm.validarDatosRegistro("Ali", null, null, null, null)

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
        assertEquals("El nom ha de tenir mínim 5 caràcters", estado.errorNombreUsuario)
    }

    // 4. Test nombre inválido (caracteres no permitidos)
    @Test
    fun `nombre inválido por caracteres no permitidos`() {
        val vm = getViewModel()
        vm.validarDatosRegistro("Alicia123", null, null, null, null)

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
        assertEquals("El nom només pot contenir lletres", estado.errorNombreUsuario)
    }

    // 5. Test email válido
    @Test
    fun `email válido`() {
        val vm = getViewModel()
        vm.validarDatosRegistro(null, "user@test.com", null, null, null)

        val estado = vm.validacionDatos.value
        assertNull(estado!!.errorEmailUsuario)
    }

    // 6. Test email inválido
    @Test
    fun `email inválido`() {
        val vm = getViewModel()
        vm.validarDatosRegistro(null, "user@mal", null, null, null)

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
        assertEquals("El format de l'email no és vàlid", estado.errorEmailUsuario)
    }

    // 7. Test edad válida
    @Test
    fun `edad válida`() {
        val vm = getViewModel()
        vm.validarDatosRegistro(null, null, "20", null, null)

        val estado = vm.validacionDatos.value
        assertNull(estado!!.errorEdadUsuario)
    }

    // 8. Test edad inválida (cero)
    @Test
    fun `edad inválida por ser cero`() {
        val vm = getViewModel()
        vm.validarDatosRegistro(null, null, "0", null, null)

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
        assertEquals("L'edat ha de ser major que 0", estado.errorEdadUsuario)
    }

    // 9. Test edad inválida (menor de 16)
    @Test
    fun `edad inválida por ser menor de 16`() {
        val vm = getViewModel()
        vm.validarDatosRegistro(null, null, "15", null, null)

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
        assertEquals("Has de ser major de 16 anys", estado.errorEdadUsuario)
    }

    // 10. Test edad inválida (formato no numérico)
    @Test
    fun `edad inválida por formato no numérico`() {
        val vm = getViewModel()
        vm.validarDatosRegistro(null, null, "abc", null, null)

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
        assertEquals("L'edat ha de ser un número", estado.errorEdadUsuario)
    }

    // 11. Test contraseña válida
    @Test
    fun `contraseña válida`() {
        val vm = getViewModel()
        vm.validarDatosRegistro(null, null, null, "Pass@1234", null)

        val estado = vm.validacionDatos.value
        assertNull(estado!!.errorContrasenyaUsuario)
    }

    // 12. Test contraseña inválida (sin número)
    @Test
    fun `contraseña inválida sin número`() {
        val vm = getViewModel()
        vm.validarDatosRegistro(null, null, null, "Contrasenya@", null)

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
        assertEquals("La contrasenya ha de contenir al menys un número", estado.errorContrasenyaUsuario)
    }

    // 13. Test contraseña inválida (sin símbolo especial)
    @Test
    fun `contraseña inválida sin símbolo especial`() {
        val vm = getViewModel()
        vm.validarDatosRegistro(null, null, null, "Contrasenya123", null)

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
        assertEquals("La contrasenya ha de contenir al menys un símbol especial", estado.errorContrasenyaUsuario)
    }

    // 14. Test confirmación de contraseña válida
    @Test
    fun `confirmación de contraseña válida`() {
        val vm = getViewModel()
        vm.validarDatosRegistro(null, null, null, "Pass@1234", "Pass@1234")

        val estado = vm.validacionDatos.value
        assertNull(estado!!.errorConfirmacionContrasenya)
    }

    // 15. Test confirmación de contraseña inválida
    @Test
    fun `confirmación de contraseña inválida`() {
        val vm = getViewModel()
        vm.validarDatosRegistro(null, null, null, "Pass@1234", "OtraContraseña@1")

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
        assertEquals("Les contrasenyes no coincideixen", estado.errorConfirmacionContrasenya)
    }

    // 16. Test avatar válido
    @Test
    fun `avatar válido`() {
        val vm = getViewModel()
        vm.validarDatosRegistro(null, null, null, null, null, "avataruser1.png")

        val estado = vm.validacionDatos.value
        assertNull(estado!!.errorAvatar)
    }

    // 17. Test avatar inválido
    @Test
    fun `avatar inválido`() {
        val vm = getViewModel()
        vm.validarDatosRegistro(null, null, null, null, null, "imagenloca.png")

        val estado = vm.validacionDatos.value
        assertFalse(estado!!.esValido)
        assertEquals("Has de seleccionar un avatar vàlid", estado.errorAvatar)
    }

    // 18. Test selección de avatar
    @Test
    fun `selección de avatar`() {
        val vm = getViewModel()
        vm.seleccionarAvatar("avataruser2.png")

        assertEquals("avataruser2.png", vm.avatarSeleccionado.value)
    }

    // 19. Test registro completo
    @Test
    fun `proceso de registro completo`() {
        val vm = getViewModel()
        val observerCargando = Observer<Boolean> {}
        val observerCompletado = Observer<Boolean> {}

        vm.cargando.observeForever(observerCargando)
        vm.registroCompletado.observeForever(observerCompletado)

        vm.registrarUsuario(
            "Alicia",
            "alicia@gmail.com",
            "22",
            "Contrasenya@123",
            "Contrasenya@123",
            "avataruser1.png"
        )

        assertTrue(vm.registroCompletado.value == true)
        assertFalse(vm.cargando.value == true)

        vm.cargando.removeObserver(observerCargando)
        vm.registroCompletado.removeObserver(observerCompletado)
    }

    // 20. Test limpiar estado
    @Test
    fun `limpiar estado`() {
        val vm = getViewModel()

        // Primero establecemos algunos valores
        vm.validarDatosRegistro("Ali", null, null, null, null)
        vm.registrarUsuario("Alicia", "alicia@gmail.com", "22", "Contrasenya@123", "Contrasenya@123", "avataruser1.png")

        // Luego limpiamos
        vm.limpiarEstado()

        // Verificamos que todo se ha limpiado
        assertTrue(vm.validacionDatos.value!!.esValido)
        assertFalse(vm.registroCompletado.value == true)
        assertFalse(vm.cargando.value == true)
    }
}