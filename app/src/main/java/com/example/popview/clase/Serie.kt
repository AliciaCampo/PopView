package com.example.popview.clase

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
) /*: Titulo(nombre, genero, duracion, plataforma) {
    override fun mostrarInformacion() {
        super.mostrarInformacion()
        println(" Data del primer episodi:$fechaPrimerEpisodio")
        if (fechaUltimoEpisodio != null) {
            println(" Data del últim episodi: $fechaUltimoEpisodio")
        } else {
            println("La sèrie encara está en emissió.")
        }
        println("Número de temporades: $numeroDeTemporadas")
        println("La sèrie està acabada?: ${if (estaAcabada) "Sí" else "No"}")
    }
}*/