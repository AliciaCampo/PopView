package com.example.popview

import java.io.Serializable

data class Titulo(
    val imagen: Int,          // Recurso de la imagen
    val nombre: String,               // Título del contenido
    val description: String,         // Descripción del contenido
    val platforms: List<String>,     // Plataformas disponibles
    val rating: Float,               // Puntuación
    val comments: List<Item>         // Lista de comentarios
) : Serializable
