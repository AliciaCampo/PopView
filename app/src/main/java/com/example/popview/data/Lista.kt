package com.example.popview.data
import java.io.Serializable
data class Lista(
    val titulo: String,
    val descripcion: String = "",
    val esPrivada: Boolean = false,
    //val peliculas: List<String> = emptyList()
    val titulos: MutableList<Titulo> = mutableListOf() // Lista de objetos Titulo
) : Serializable // Implementar Serializable