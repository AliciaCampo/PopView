package com.example.popview.clase

class Pelicula(
    imagen: Int,
    nombre: String,
    genero: String,
    duracion: Int,
    val director: String,
    plataforma: String
)/* : Titulo(nombre, genero, duracion, plataforma ) {

    override fun mostrarInformacion() {
        super.mostrarInformacion()
        println("Director: $director")
    }
}*/