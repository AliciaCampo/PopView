package com.example.popview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ValoracionTituloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_valoracion_titulo)

        // Recibir el objeto Titulo
        val titulo: Titulo = intent.getSerializableExtra("titulo") as Titulo

        // Configurar los elementos de la plantilla
        val imageView = findViewById<ImageView>(R.id.imageContent)
        val textTitle = findViewById<TextView>(R.id.textTitle)
        val textDescription = findViewById<TextView>(R.id.textDescription)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)

        // Configurar la imagen, título, descripción y puntuación
        imageView.setImageResource(titulo.imagen)
        textTitle.text = titulo.nombre
        textDescription.text = titulo.description
        ratingBar.rating = titulo.rating
        // Configurar botón de retroceso
        val imageButtonEnrere: ImageButton = findViewById(R.id.imageButtonEnrere)
        imageButtonEnrere.setOnClickListener {
            // Volver a la pantalla principal
            val intentEnrere = Intent(this, BuscarActivity::class.java)
            startActivity(intentEnrere)
            finish()
        }
        // Configurar plataformas (máximo 3, basado en tu plantilla)
        val platformIcons = listOf(
            findViewById<ImageView>(R.id.platformIcon1),
            findViewById<ImageView>(R.id.platformIcon2),
            findViewById<ImageView>(R.id.platformIcon3)
        )

        platformIcons.forEachIndexed { index, imageView ->
            if (index < titulo.platforms.size) {
                val platform = titulo.platforms[index]
                imageView.visibility = View.VISIBLE
                imageView.setImageResource(getPlatformIcon(platform)) // Función para asignar íconos
            } else {
                imageView.visibility = View.GONE
            }
        }



        // Configurar RecyclerView para Comentaris dinámicos
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Crear lista de Comentaris o ítems
        val items = listOf(
            Item("Comentari 1", "Això és un comentari 1.", R.drawable.account, 4.0f),
            Item("Comentari 2", "Això és un comentari 2.", R.drawable.account, 3.5f),
            Item("Comentari 3", "Això és un comentari 3.", R.drawable.account, 4.0f)
        )
        // Configurar adaptador
        val adapter = ValoracionTituloAdapter(items)
        recyclerView.adapter = adapter
    }
    fun getPlatformIcon(platform: String): Int {
        return when (platform) {
            "Netflix" -> R.drawable.logo_netflix
            "Amazon Prime" -> R.drawable.logo_amazon_prime
            "HBO" -> R.drawable.logo_hbo
            "Disney" -> R.drawable.logo_disneyplus
            "Movistar" -> R.drawable.logo_movistarplus
            "Jazztel" -> R.drawable.logo_jazzteltv
            else -> R.drawable.logo
        }
    }

}