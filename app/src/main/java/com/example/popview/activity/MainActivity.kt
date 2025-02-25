package com.example.popview.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.popview.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Referencia al ProgressBar
        progressBar = findViewById(R.id.progressBar)

        lifecycleScope.launch {
            // Simula la carga de la barra de progreso durante 5 segundos
            cargarBarraProgreso()
            // Una vez completada la carga, inicia HomeActivity
            withContext(Dispatchers.Main) {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    // Simula la carga de la barra de progreso durante exactamente 5 segundos
    private suspend fun cargarBarraProgreso() {
        val tiempoTotal = 5000L // 5 segundos en milisegundos
        val interval = 50L // Intervalo de actualización en milisegundos
        val pasos = (tiempoTotal / interval).toInt() // Número de pasos para llegar al 100%
        var progress = 0

        withContext(Dispatchers.Main) {
            while (progress <= 100) {
                progressBar.progress = progress
                delay(interval)
                progress += (100 / pasos) // Incremento proporcional para llegar a 100 en 5 segundos
            }
            progressBar.progress = 100 // Asegura que termine en 100%
        }
    }
}