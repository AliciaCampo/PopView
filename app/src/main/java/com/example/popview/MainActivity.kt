package com.example.popview

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Referencia al ProgressBar
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        // Tiempo total de espera en milisegundos
        val totalTime = 5000
        val interval = 50 // Intervalo de actualización en milisegundos
        val handler = Handler(Looper.getMainLooper())
        var progress = 0
        val runnable = object : Runnable {
            override fun run() {
                progress += (100 * interval / totalTime) // Incrementar progreso basado en el tiempo
                if (progress <= 100) {
                    progressBar.progress = progress
                    handler.postDelayed(this, interval.toLong())
                } else {
                    // Cuando el progreso llega al máximo, cambia de pantalla
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        handler.post(runnable)
    }
}