package com.example.popview.data

import java.io.Serializable

data class Titulo(
    val imagen: String,          // Recurso de la imagen
    val nombre: String,               // Título del contenido
    val description: String, // Descripción del contenido
    val genero: String, // Género del título
    val edadRecomendada: Int,       // Edad recomendada
    val platforms: List<String>,     // Plataformas disponibles
    val rating: Float,               // Puntuación
    val comments: List<Item> = listOf(), // Lista de comentarios
    val id: Int = 0 // ID generado automáticamente por el servidor
) : Serializable
