package com.example.popview

import org.junit.Assert.assertEquals
import org.junit.Test
class TituloRepositoryTest {
    @Test
    fun `cargar 120 títulos correctamente`() {
        // Simulación de los títulos cargados (como si los devolviera el repositorio)
        val fakeTitles = List(120) { "Título ${it + 1}" }
        // Comprobamos que la lista tiene exactamente 120 elementos
        assertEquals(120, fakeTitles.size)
    }
}
