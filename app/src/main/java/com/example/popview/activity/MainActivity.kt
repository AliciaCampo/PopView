package com.example.popview.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.widget.ProgressBar
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
         progressBar = findViewById<ProgressBar>(R.id.progressBar)
        //llamada a la API y se mide el tiempo
        lifecycleScope.launch {
            val tiempoCarga = verificarYCrearTitulosIniciales()
            //ajuste de la barra de progreso
            cargaBarraProgreso(tiempoCarga)
        }
    }
    private suspend fun verificarYCrearTitulosIniciales(): Long {
        return withContext(Dispatchers.IO) {
            measureTimeMillis {
                try{
                    val api = PopViewAPI().API()
                    val titulos = api.getAllTitols()
                    if(titulos.isEmpty()){
                        val titulosIniciales = listOf(
                            Titulo(
                                id = 1,
                                imagen = "sabrina.jpg",
                                nombre = "Sabrina",
                                description = "Descripció de Sabrina",
                                genero = "Fantasia",
                                edadRecomendada = 12,
                                platforms = listOf("Netflix", "Amazon Prime"),
                                rating = 3.5f,
                                comments = listOf()
                            ),
                            Titulo(
                                id = 2,
                                imagen = "strangerthingscuatro.jpg",
                                nombre = "Stranger Things",
                                description = "Una sèrie de misteri",
                                genero = "Ciència Ficció",
                                edadRecomendada = 16,
                                platforms = listOf("Netflix"),
                                rating = 4f,
                                comments = listOf()
                            ),
                            Titulo(
                                id = 3,
                                imagen = "orange_is_the_new_black.jpg",
                                nombre = "Orange is the new black",
                                description = "Descripció de Orange is the new black",
                                genero = "Drama",
                                edadRecomendada = 18,
                                platforms = listOf("Netflix"),
                                rating = 4f,
                                comments = listOf()
                            ),
                            Titulo(
                                id = 4,
                                imagen = "wednesdaymiercoles.jpg",
                                nombre = "Miercoles",
                                description = "Descripció de Miercoles",
                                genero = "Comèdia",
                                edadRecomendada = 12,
                                platforms = listOf("Netflix"),
                                rating = 4f,
                                comments = listOf()
                            ),
                            Titulo(
                                id = 5,
                                imagen = "deadpoolylobezno.jpg",
                                nombre = "Deadpool y Lobezno",
                                description = "Descripció de Deadpool y Lobezno",
                                genero = "Acció",
                                edadRecomendada = 18,
                                platforms = listOf("Netflix"),
                                rating = 4f,
                                comments = listOf()
                            ),
                            Titulo(
                                id = 6,
                                imagen = "delrevesdos.jpg",
                                nombre = "Del Reves 2",
                                description = "Descripció de Del Reves 2",
                                genero = "Animació",
                                edadRecomendada = 6,
                                platforms = listOf("Disney"),
                                rating = 4f,
                                comments = listOf()
                            ),
                            Titulo(
                                id = 7,
                                imagen = "respira.jpg",
                                nombre = "Respira",
                                description = "Relaxa't i respira",
                                genero = "Drama",
                                edadRecomendada = 16,
                                platforms = listOf("Netflix"),
                                rating = 4f,
                                comments = listOf()
                            ),
                            Titulo(
                                id = 8,
                                imagen = "beetlejuice2.jpg",
                                nombre = "Beetlejuice 2",
                                description = "Descripció de Beetlejuice 2",
                                genero = "Comèdia",
                                edadRecomendada = 12,
                                platforms = listOf("Netflix"),
                                rating = 4f,
                                comments = listOf()
                            ),
                            Titulo(
                                id = 9,
                                imagen = "jokerdos.jpg",
                                nombre = "Joker 2",
                                description = "Descripció de Joker 2",
                                genero = "Drama",
                                edadRecomendada = 18,
                                platforms = listOf("Netflix"),
                                rating = 4f,
                                comments = listOf()
                            ),
                            Titulo(
                                id = 10,
                                imagen = "juegodelcalamar.jpg",
                                nombre = "Juego del Calamar",
                                description = "Descripció de Juego del Calamar",
                                genero = "Acció",
                                edadRecomendada = 18,
                                platforms = listOf("Netflix"),
                                rating = 4f,
                                comments = listOf()
                            ),
                            Titulo(
                                id = 11,
                                imagen = "robotsalvaje.jpg",
                                nombre = "Robot Salvaje",
                                description = "Descripció de Robot Salvaje",
                                genero = "Ciència Ficció",
                                edadRecomendada = 12,
                                platforms = listOf("Netflix"),
                                rating = 4f,
                                comments = listOf()
                            ),
                            Titulo(
                                id = 12,
                                imagen = "venom3.jpg",
                                nombre = "Venom 3",
                                description = "Descripció de Venom 3",
                                genero = "Acció",
                                edadRecomendada = 16,
                                platforms = listOf("Netflix"),
                                rating = 4f,
                                comments = listOf()
                            ),
                            Titulo(
                                id = 13,
                                imagen = "cobrakai.jpg",
                                nombre = "Cobra Kai",
                                description = "Descripció de Cobra Kai",
                                genero = "Acció",
                                edadRecomendada = 12,
                                platforms = listOf("Netflix"),
                                rating = 4f,
                                comments = listOf()
                            ),
                            Titulo(
                                id = 14,
                                imagen = "casadepapel.jpg",
                                nombre = "Casa de Papel",
                                description = "Descripció de Casa de Papel",
                                genero = "Acció",
                                edadRecomendada = 16,
                                platforms = listOf("Netflix"),
                                rating = 4f,
                                comments = listOf()
                            )
                        )
                        for (titulo in titulosIniciales){
                            api.createTitulo(titulo)
                        }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
                }
        }
    }
    //metodo que carga la barra de progreso
    private suspend fun cargaBarraProgreso(tiempoCarga: Long){
        val tiempoTotal= if(tiempoCarga<2000) 2000 else tiempoCarga
        val interval =50
        var progress = 0
        withContext(Dispatchers.Main){
            while(progress<=100){
                progressBar.progress = progress
                progress += interval
                delay(interval.toLong())
                progress +=(100 * interval / tiempoTotal).toInt()
            }
        }
    }
}