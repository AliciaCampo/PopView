package com.example.popview.data

data class Item(
    val id: Int=0,
    val title: String,
    val imageUrl: String,
    val description: String,
    val rating: Float,
    val platforms: String ="Netflix",
    val comments: List<Item> = emptyList(),
    val edadRecomendad : Int=18,
    val genero: String="Acción"
)