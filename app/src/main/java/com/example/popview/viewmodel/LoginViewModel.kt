package com.example.popview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class EstadoLogin(
    val esValido: Boolean,
    val errorUserID: String?   = null,
    val errorPassword: String? = null
)

class LoginViewModel : ViewModel() {

    companion object {
        const val ERROR_INCORRECT_CREDENTIALS = "ID o contrasenya incorrectes"
    }

    private val _loginState = MutableLiveData<EstadoLogin>()
    val loginState: LiveData<EstadoLogin> = _loginState

    fun validar(userID: String, password: String) {
        // Campos vacíos
        if (userID.isBlank() || password.isBlank()) {
            _loginState.value = EstadoLogin(
                esValido      = false,
                errorUserID   = if (userID.isBlank()) "L'ID d'usuari és obligatori" else null,
                errorPassword = if (password.isBlank()) "La contrasenya és obligatòria" else null
            )
            return
        }

        // Credenciales estáticas
        if (userID != "user2131" || password != "pirineus") {
            _loginState.value = EstadoLogin(
                esValido      = false,
                errorUserID   = null,
                errorPassword = ERROR_INCORRECT_CREDENTIALS
            )
            return
        }

        // Si todo OK
        _loginState.value = EstadoLogin(esValido = true)
    }
}
