package com.example.popview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

data class EstadoUsuario(
    var esValido: Boolean,
    var errorNombreUsuario: String?,
    var errorEmailUsuario: String?,
    var errorEdadUsuario: String?,
    var errorContrasenyaUsuario: String?
)

class UsuarioViewModel : ViewModel() {

    private var nombreUsuario: String = ""
    private var emailUsuario: String = ""
    private var edadUsuario: Int = 0
    private var contrasenyaUsuario: String = ""

    private val _validacionDatos = MutableLiveData<EstadoUsuario>()
    val validacionDatos: LiveData<EstadoUsuario> = _validacionDatos

    // Método para validar todos los datos del usuario
    fun validarDatosUsuario(
        nombre: String?,
        email: String?,
        edad: Int?,
        contrasenya: String?
    ) {
        val errores = EstadoUsuario(
            esValido = true,
            errorNombreUsuario = null,
            errorEmailUsuario = null,
            errorEdadUsuario = null,
            errorContrasenyaUsuario = null
        )

        // Validar cada parámetro de manera independiente
        validarNombre(nombre, errores)
        validarEmail(email, errores)
        validarEdad(edad, errores)
        validarContrasenya(contrasenya, errores)

        // Actualizar el estado de validación
        _validacionDatos.value = errores
    }

    // Método para validar el nombre
    private fun validarNombre(nombre: String?, errores: EstadoUsuario) {
        if (nombre.isNullOrEmpty()) {
            errores.errorNombreUsuario = "El nom no pot estar buit"
            errores.esValido = false
        } else {
            nombreUsuario = nombre
        }
    }

    // Método para validar el email
    private fun validarEmail(email: String?, errores: EstadoUsuario) {
        if (email.isNullOrEmpty()) {
            errores.errorEmailUsuario = "L'email no pot estar buit"
            errores.esValido = false
        } else {
            emailUsuario = email
        }
    }

    // Método para validar la edad
    private fun validarEdad(edad: Int?, errores: EstadoUsuario) {
        if (edad == null || edad <= 0) {
            errores.errorEdadUsuario = "L'edat ha de ser major que 0"
            errores.esValido = false
        } else {
            edadUsuario = edad
        }
    }

    // Método para validar la contraseña
    private fun validarContrasenya(contrasenya: String?, errores: EstadoUsuario) {
        if (contrasenya.isNullOrEmpty()) {
            errores.errorContrasenyaUsuario = "La contrasenya no pot estar buida"
            errores.esValido = false
        } else {
            contrasenyaUsuario = contrasenya
        }
    }
}
