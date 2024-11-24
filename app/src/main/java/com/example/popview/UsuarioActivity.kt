package com.example.popview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 1. Referenciar las CardViews
        val card1 = findViewById<CardView>(R.id.card01)
        val card2 = findViewById<CardView>(R.id.card02)
        val card3 = findViewById<CardView>(R.id.card03)

        // 2. Configurar los clics de las CardViews
        card1.setOnClickListener {
            // Datos que quieres pasar
            val listaData = "Serie: Series per veure, Privada"
            val intent = Intent(this, EditLista::class.java)
            intent.putExtra("listaData", listaData) // Pasando datos al EditLista
            startActivity(intent)
        }

        card2.setOnClickListener {
            val listaData = "Película: Pelicules de terror, Pública"
            val intent = Intent(this, EditLista::class.java)
            intent.putExtra("listaData", listaData) // Pasando datos al EditLista
            startActivity(intent)
        }

        card3.setOnClickListener {
            val listaData = "Serie: Millors series, Privada"
            val intent = Intent(this, EditLista::class.java)
            intent.putExtra("listaData", listaData) // Pasando datos al EditLista
            startActivity(intent)
        }
        val btnGuardar = findViewById<Button>(R.id.btnCrearLista)
        btnGuardar.setOnClickListener {
            val intent = Intent(this, CrearListaActivity::class.java)
            startActivity(intent)
        }
    }
}