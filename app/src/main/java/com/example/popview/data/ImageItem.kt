package com.example.popview.data

import java.io.Serializable

data class ImageItem(
    val imageUrl: String, // URL de la imagen
    val titulo: Titulo    // Objeto Titulo
) : Serializable
