package com.example.popview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class EstadoLogin(
    var esValido: Boolean,
    var errorNombreUsuario: String? = null,
    var errorEmailUsuario: String? = null,
    var errorContrasenyaUsuario: String? = null
)

class LoginViewModel : ViewModel() {

    private val _validacionLogin = MutableLiveData<EstadoLogin>()
    val validacionLogin: LiveData<EstadoLogin> = _validacionLogin

    fun validarCredenciales(nombre: String?, email: String?, contrasenya: String?) {
        val estado = EstadoLogin(esValido = true)

        validarNombre(nombre, estado)
        validarEmail(email, estado)
        validarContrasenya(contrasenya, estado)

        _validacionLogin.value = estado
    }

    private fun validarNombre(nombre: String?, estado: EstadoLogin) {
        if (nombre.isNullOrBlank() || nombre.length < 5) {
            estado.errorNombreUsuario = "El nom ha de tenir mínim 5 caràcters"
            estado.esValido = false
        }
    }

    private fun validarEmail(email: String?, estado: EstadoLogin) {
        val emailRegex = Regex("^[\\w.-]+@[\\w.-]+\\.(com|es|org|net)\$")
        if (email.isNullOrBlank() || !emailRegex.matches(email)) {
            estado.errorEmailUsuario = "L'email no és vàlid"
            estado.esValido = false
        }
    }

    private fun validarContrasenya(contrasenya: String?, estado: EstadoLogin) {
        val regexContrasenya = Regex("^(?=.*[0-9])(?=.*[!@#\$%^&*()_+=\\-{}|:;\"'<>,.?/]).{8,}\$")
        if (contrasenya.isNullOrBlank() || !regexContrasenya.matches(contrasenya)) {
            estado.errorContrasenyaUsuario = "La contrasenya ha de tenir mínim 8 caràcters, un número i un símbol especial"
            estado.esValido = false
        }
    }
}
