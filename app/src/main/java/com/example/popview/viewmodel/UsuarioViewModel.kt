package com.example.popview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

data class EstadoUsuario(
    var esValido: Boolean,
    var errorNombreUsuario: String? = null,
    var errorEmailUsuario: String? = null,
    var errorEdadUsuario: String? = null,
    var errorContrasenyaUsuario: String? = null
)

class UsuarioViewModel : ViewModel() {

    private val _validacionDatos = MutableLiveData<EstadoUsuario>()
    val validacionDatos: LiveData<EstadoUsuario> = _validacionDatos

    private val _avatarValido = MutableLiveData<Boolean>()
    val avatarValido: LiveData<Boolean> = _avatarValido

    fun validarDatosUsuario(
        nombre: String?,
        email: String?,
        edad: Int?,
        contrasenya: String?,
        avatar: String? = null
    ) {
        val estado = EstadoUsuario(esValido = true)

        validarNombre(nombre, estado)
        validarEmail(email, estado)
        validarEdad(edad, estado)
        validarContrasenya(contrasenya, estado)
        validarAvatar(avatar, _avatarValido)

        _validacionDatos.value = estado
    }

    private fun validarNombre(nombre: String?, estado: EstadoUsuario) {
        if (nombre.isNullOrBlank() || nombre.length < 5) {
            estado.errorNombreUsuario = "El nom ha de tenir mínim 5 caràcters"
            estado.esValido = false
        }
    }

    private fun validarEmail(email: String?, estado: EstadoUsuario) {
        val emailRegex = Regex("^[\\w.-]+@[\\w.-]+\\.(com|es|org|net)\$")
        if (email.isNullOrBlank() || !emailRegex.matches(email)) {
            estado.errorEmailUsuario = "L'email no és vàlid"
            estado.esValido = false
        }
    }

    private fun validarEdad(edad: Int?, estado: EstadoUsuario) {
        if (edad == null || edad <= 0) {
            estado.errorEdadUsuario = "L'edat ha de ser major que 0"
            estado.esValido = false
        }
    }

    private fun validarContrasenya(contrasenya: String?, estado: EstadoUsuario) {
        val regexContrasenya = Regex("^(?=.*[0-9])(?=.*[!@#\$%^&*()_+=\\-{}|:;\"'<>,.?/]).{8,}\$")
        if (contrasenya.isNullOrBlank() || !regexContrasenya.matches(contrasenya)) {
            estado.errorContrasenyaUsuario = "La contrasenya ha de tenir mínim 8 caràcters, un número i un símbol especial"
            estado.esValido = false
        }
    }

    // Modificación de la validación de avatar para los recursos del drawable
    fun validarAvatar(avatar: String?, estado: MutableLiveData<Boolean>) {
        val recursosDrawable = listOf("avataruser1.png", "avataruser2.png", "avataruser3.png")
        estado.value = avatar != null && recursosDrawable.contains(avatar)
    }
}

