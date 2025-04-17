package com.example.popview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

data class EstadoRegistro(
    var esValido: Boolean = true,
    var errorNombreUsuario: String? = null,
    var errorEmailUsuario: String? = null,
    var errorEdadUsuario: String? = null,
    var errorContrasenyaUsuario: String? = null,
    var errorConfirmacionContrasenya: String? = null,
    var errorAvatar: String? = null
)

data class DatosRegistro(
    val nombre: String,
    val email: String,
    val edad: Int,
    val contrasenya: String,
    val avatar: String?
)

class RegistroViewModel : ViewModel() {

    private val _validacionDatos = MutableLiveData<EstadoRegistro>()
    val validacionDatos: LiveData<EstadoRegistro> = _validacionDatos

    private val _avatarSeleccionado = MutableLiveData<String?>()
    val avatarSeleccionado: LiveData<String?> = _avatarSeleccionado

    private val _registroCompletado = MutableLiveData<Boolean>()
    val registroCompletado: LiveData<Boolean> = _registroCompletado

    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

    fun seleccionarAvatar(avatar: String) {
        _avatarSeleccionado.value = avatar
    }

    fun validarDatosRegistro(
        nombre: String?,
        email: String?,
        edad: String?,
        contrasenya: String?,
        confirmacionContrasenya: String?,
        avatar: String? = _avatarSeleccionado.value
    ): Boolean {
        val estado = EstadoRegistro()

        validarNombre(nombre, estado)
        validarEmail(email, estado)
        validarEdad(edad, estado)
        validarContrasenya(contrasenya, estado)
        validarConfirmacionContrasenya(contrasenya, confirmacionContrasenya, estado)
        validarAvatar(avatar, estado)

        _validacionDatos.value = estado
        return estado.esValido
    }

    fun registrarUsuario(
        nombre: String,
        email: String,
        edad: String,
        contrasenya: String,
        confirmacionContrasenya: String,
        avatar: String? = _avatarSeleccionado.value
    ) {
        if (validarDatosRegistro(nombre, email, edad, contrasenya, confirmacionContrasenya, avatar)) {
            _cargando.value = true

            // Simular proceso de registro (aquí se conectaría con el repositorio)
            val edadInt = edad.toIntOrNull() ?: 0
            val datosRegistro = DatosRegistro(nombre, email, edadInt, contrasenya, avatar)

            // En un caso real, aquí iría la llamada al repositorio para guardar el usuario
            // Ejemplo: usuarioRepository.registrarUsuario(datosRegistro)

            // Simulación de registro exitoso
            _registroCompletado.value = true
            _cargando.value = false
        }
    }

    private fun validarNombre(nombre: String?, estado: EstadoRegistro) {
        when {
            nombre.isNullOrBlank() -> {
                estado.errorNombreUsuario = "El nom és obligatori"
                estado.esValido = false
            }
            nombre.length < 5 -> {
                estado.errorNombreUsuario = "El nom ha de tenir mínim 5 caràcters"
                estado.esValido = false
            }
            nombre.length > 50 -> {
                estado.errorNombreUsuario = "El nom no pot superar els 50 caràcters"
                estado.esValido = false
            }
            !nombre.matches(Regex("^[a-zA-ZáàéèíìóòúùÁÀÉÈÍÌÓÒÚÙçÇñÑ ]+$")) -> {
                estado.errorNombreUsuario = "El nom només pot contenir lletres"
                estado.esValido = false
            }
        }
    }

    private fun validarEmail(email: String?, estado: EstadoRegistro) {
        val emailRegex = Regex("^[\\w.-]+@[\\w.-]+\\.(com|es|org|net)\$")
        when {
            email.isNullOrBlank() -> {
                estado.errorEmailUsuario = "L'email és obligatori"
                estado.esValido = false
            }
            !emailRegex.matches(email) -> {
                estado.errorEmailUsuario = "El format de l'email no és vàlid"
                estado.esValido = false
            }
            email.length > 100 -> {
                estado.errorEmailUsuario = "L'email no pot superar els 100 caràcters"
                estado.esValido = false
            }
        }
    }

    private fun validarEdad(edad: String?, estado: EstadoRegistro) {
        val edadInt = edad?.toIntOrNull()
        when {
            edad.isNullOrBlank() -> {
                estado.errorEdadUsuario = "L'edat és obligatòria"
                estado.esValido = false
            }
            edadInt == null -> {
                estado.errorEdadUsuario = "L'edat ha de ser un número"
                estado.esValido = false
            }
            edadInt <= 0 -> {
                estado.errorEdadUsuario = "L'edat ha de ser major que 0"
                estado.esValido = false
            }
            edadInt < 16 -> {
                estado.errorEdadUsuario = "Has de ser major de 16 anys"
                estado.esValido = false
            }
            edadInt > 120 -> {
                estado.errorEdadUsuario = "L'edat introduïda no és vàlida"
                estado.esValido = false
            }
        }
    }

    private fun validarContrasenya(contrasenya: String?, estado: EstadoRegistro) {
        val regexContrasenya = Regex("^(?=.*[0-9])(?=.*[!@#\$%^&*()_+=\\-{}|:;\"'<>,.?/]).{8,}\$")
        when {
            contrasenya.isNullOrBlank() -> {
                estado.errorContrasenyaUsuario = "La contrasenya és obligatòria"
                estado.esValido = false
            }
            contrasenya.length < 8 -> {
                estado.errorContrasenyaUsuario = "La contrasenya ha de tenir mínim 8 caràcters"
                estado.esValido = false
            }
            !contrasenya.contains(Regex("[0-9]")) -> {
                estado.errorContrasenyaUsuario = "La contrasenya ha de contenir al menys un número"
                estado.esValido = false
            }
            !contrasenya.contains(Regex("[!@#\$%^&*()_+=\\-{}|:;\"'<>,.?/]")) -> {
                estado.errorContrasenyaUsuario = "La contrasenya ha de contenir al menys un símbol especial"
                estado.esValido = false
            }
            !regexContrasenya.matches(contrasenya) -> {
                estado.errorContrasenyaUsuario = "La contrasenya no compleix els requisits mínims"
                estado.esValido = false
            }
        }
    }

    private fun validarConfirmacionContrasenya(contrasenya: String?, confirmacion: String?, estado: EstadoRegistro) {
        when {
            confirmacion.isNullOrBlank() -> {
                estado.errorConfirmacionContrasenya = "La confirmació de contrasenya és obligatòria"
                estado.esValido = false
            }
            contrasenya != confirmacion -> {
                estado.errorConfirmacionContrasenya = "Les contrasenyes no coincideixen"
                estado.esValido = false
            }
        }
    }

    private fun validarAvatar(avatar: String?, estado: EstadoRegistro) {
        val recursosDrawable = listOf("avataruser1.png", "avataruser2.png", "avataruser3.png")
        if (avatar == null || !recursosDrawable.contains(avatar)) {
            estado.errorAvatar = "Has de seleccionar un avatar vàlid"
            estado.esValido = false
        }
    }

    fun limpiarEstado() {
        _validacionDatos.value = EstadoRegistro()
        _registroCompletado.value = false
        _cargando.value = false
    }
}