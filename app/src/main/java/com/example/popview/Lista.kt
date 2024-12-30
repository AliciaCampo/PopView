package com.example.popview
import java.io.Serializable
data class Lista(
    val titulo: String,
    val descripcion: String = "",
    val esPrivada: Boolean = false,
    val peliculas: List<String> = emptyList()
) : Serializable // Implementar Serializable