package com.example.popview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

class ValoracionTituloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar la ventana para que no limite el contenido a las barras del sistema
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(R.layout.activity_valoracion_titulo)

        // Ajustar el padding de los elementos para acomodar las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars: Insets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar el botón de retroceso
        val imageButtonEnrere: ImageButton = findViewById(R.id.imageButtonEnrere)
        imageButtonEnrere.setOnClickListener {
            // Crear Intent para ir a BuscarActivity
            val intentEnrere = Intent(this@ValoracionTituloActivity, BuscarActivity::class.java)
            startActivity(intentEnrere)
            // Cerrar la actividad actual
            finish()
        }
    }
}
