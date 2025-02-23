package com.example.popview.activity

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.popview.R
import com.example.popview.data.Titulo
import com.example.popview.service.PopViewAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al ProgressBar
        progressBar = findViewById(R.id.progressBar)

        // Llamada a la API y se mide el tiempo
        lifecycleScope.launch {
            val tiempoCarga = verificarYCrearTitulosIniciales()
            // Ajuste de la barra de progreso
            cargarBarraProgreso(tiempoCarga)
        }
    }

    private suspend fun verificarYCrearTitulosIniciales(): Long {
        return withContext(Dispatchers.IO) {
            measureTimeMillis {
                try {
                    val api = PopViewAPI().API()
                    val titulosExistentes = api.getAllTitols()
                    val titulosIniciales = listOf(
                        Titulo(
                            imagen = "sabrina.jpg",
                            nombre = "Sabrina",
                            description = "Descripción de Sabrina",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix", "Amazon Prime"),
                            rating = 3.5f
                        ),
                        Titulo(
                            imagen = "strangerthings.jpg",
                            nombre = "Stranger Things",
                            description = "Una serie de misterio",
                            genero = "Ciencia ficción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "orange_is_the_new_black.jpg",
                            nombre = "Orange is the New Black",
                            description = "Descripción de Orange is the New Black",
                            genero = "Drama",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "wednesday.jpg",
                            nombre = "Wednesday",
                            description = "Descripción de Wednesday",
                            genero = "Comedia",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "deadpool_and_wolverine.jpg",
                            nombre = "Deadpool y Wolverine",
                            description = "Descripción de Deadpool y Wolverine",
                            genero = "Acción",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "del_reves_2.jpg",
                            nombre = "Del Revés 2",
                            description = "Descripción de Del Revés 2",
                            genero = "Animación",
                            edadRecomendada = 6,
                            platforms = listOf("Disney"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "respira.jpg",
                            nombre = "Respira",
                            description = "Relax and breathe",
                            genero = "Drama",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "beetlejuice_2.jpg",
                            nombre = "Beetlejuice 2",
                            description = "Descripción de Beetlejuice 2",
                            genero = "Comedia",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "joker_2.jpg",
                            nombre = "Joker 2",
                            description = "Descripción de Joker 2",
                            genero = "Drama",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "juego_del_calamar.jpg",
                            nombre = "Juego del Calamar",
                            description = "Descripción de Juego del Calamar",
                            genero = "Acción",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "robot_salvaje.jpg",
                            nombre = "Robot Salvaje",
                            description = "Descripción de Robot Salvaje",
                            genero = "Ciencia ficción",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "venom_3.jpg",
                            nombre = "Venom 3",
                            description = "Descripción de Venom 3",
                            genero = "Acción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "cobra_kai.jpg",
                            nombre = "Cobra Kai",
                            description = "Descripción de Cobra Kai",
                            genero = "Acción",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "casa_de_papel.jpg",
                            nombre = "Casa de Papel",
                            description = "Descripción de Casa de Papel",
                            genero = "Acción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        )
                    )

                    val titulosACrear = titulosIniciales.filter { titulo ->
                        titulosExistentes.none { it.nombre == titulo.nombre }
                    }

                    for (titulo in titulosACrear) {
                        api.createTitulo(titulo)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private suspend fun cargarBarraProgreso(tiempoCarga: Long) {
        val tiempoTotal = if (tiempoCarga < 2000) 2000 else tiempoCarga
        val interval = 50
        var progress = 0
        withContext(Dispatchers.Main) {
            while (progress <= 100) {
                progressBar.progress = progress
                progress += interval
                delay(interval.toLong())
                progress += (100 * interval / tiempoTotal).toInt()
            }
        }
    }
}
