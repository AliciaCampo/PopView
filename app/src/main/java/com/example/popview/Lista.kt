package com.example.popview

import java.io.Serializable

data class Lista(
    var titulo: String,
    var esPrivada: Boolean,
    val peliculas: MutableList<String> = mutableListOf()
) : Serializable
