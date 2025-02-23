package com.example.popview.data

import java.io.Serializable

data class Lista(
    val titulo: String,
    val esPrivada: Boolean = false,
    val descripcion: String = "",
    val titulos: MutableList<Titulo> = mutableListOf(),
    val id: Int = 0 // Asegúrate de que haya un campo `id` si es necesario
) : Serializable
