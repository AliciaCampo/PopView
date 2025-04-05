package com.example.popview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

data class EstadoUsuario(
    var esValid: Boolean,
    var errorNombreUsuario: String?,
    var errorEmailUsuario: String?,
    var errorEdadUsuario : Int,
    var errorContrasenyaUsuario: String?,
)

class UsuarioViewModel : ViewModel() {

    private var nombreUsuario: String = ""
    private var emailUsuario: String = ""
    private var edadUsuario: Int = 0
    private var contrasenyaUsuario: String = ""

    private val _validaciodades = MutableLiveData<EstadoUsuario>()
    val validaciodades : LiveData<EstadoUsuario> = _validaciodades

}