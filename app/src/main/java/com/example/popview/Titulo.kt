package com.example.popview

open class Titulo(
    val nombre: String,
    val genero: String,
    val duracion: Int,
    val plataforma : String
) {
    open fun mostrarInformacion() {
        println("Título: $nombre")
        println("Género: $genero")
        println("Duración: $duracion minutos")
        println("Plataforma: $plataforma")
    }
}