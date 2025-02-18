package com.example.popview.clase

import com.example.popview.data.Lista

class Usuario {
    var id: Int = 0
    var nombre: String = ""
    var imagen: String = ""
    var edad: Int = 0
    lateinit var correo: String
    lateinit var contrasenya: String
    val listas: MutableList<Lista> = mutableListOf() // Lista de objetos Lista
}