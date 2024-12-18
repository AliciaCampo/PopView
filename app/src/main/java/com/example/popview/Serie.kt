package com.example.popview

import java.util.Date

class Serie(
    nombre: String,
    genero: String,
    duracion: Int,
    val fechaPrimerEpisodio: Date,
    val fechaUltimoEpisodio: Date?,
    val estaAcabada: Boolean,
    val numeroDeTemporadas: Int,
    plataforma: String
) : Titulo(nombre, genero, duracion, plataforma) {

    override fun mostrarInformacion() {
        super.mostrarInformacion()
        println("Fecha del primer episodio: $fechaPrimerEpisodio")
        if (fechaUltimoEpisodio != null) {
            println("Fecha del último episodio: $fechaUltimoEpisodio")
        } else {
            println("La serie todavía está en emisión.")
        }
        println("Número de temporadas: $numeroDeTemporadas")
        println("¿La serie está acabada?: ${if (estaAcabada) "Sí" else "No"}")
    }
}
