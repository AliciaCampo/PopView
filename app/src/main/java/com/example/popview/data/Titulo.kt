package com.example.popview.data
import java.io.Serializable
data class Titulo(
    val id: Int,
    val imagen: String,          // Recurso de la imagen
    val nombre: String,               // Título del contenido
    val description: String, // Descripción del contenido
    val genero : String, //genero titulo
    val edadRecomendada: Int,       // Edad recomendada
    val platforms: List<String>,     // Plataformas disponibles
    val rating: Float,               // Puntuación
    val comments: List<Item>         // Lista de comentarios
) : Serializable