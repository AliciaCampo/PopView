package com.example.popview

open class Titulo(
    val nombre: String,
    val genero: String,
    val duracion: Int,
    val plataforma : String
) {
    open fun mostrarInformacion() {
        println("Títol: $nombre")
        println("Gènere: $genero")
        println("Duració: $duracion minuts")
        println("Plataforma: $plataforma")
    }
}