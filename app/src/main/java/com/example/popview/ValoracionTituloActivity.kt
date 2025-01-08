package com.example.popview

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ValoracionTituloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_valoracion_titulo)
        // Recuperar el ID del recurso drawable pasado por el Intent
        val imageResource = intent.getIntExtra("imageResource", -1) // Valor predeterminado -1
        // Si el ID del recurso es válido, mostrar la imagen
        if (imageResource != -1) {
            val imageView = findViewById<ImageView>(R.id.imageView)
            imageView.setImageResource(imageResource)
        }
        // Configurar botón de retroceso
        val imageButtonEnrere: ImageButton = findViewById(R.id.imageButtonEnrere)
        imageButtonEnrere.setOnClickListener {
            // Volver a la pantalla principal
            val intentEnrere = Intent(this, BuscarActivity::class.java)
            startActivity(intentEnrere)
            finish()
        }
        // Configurar RecyclerView para Comentaris dinámicos
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Crear lista de Comentaris o ítems
        val items = listOf(
            Item("Comentari 1", "Això és un comentari 1.", R.drawable.deadpoolylobezno, 4.0f),
            Item("Comentari 2", "Això és un comentari 2.", R.drawable.deadpoolylobezno, 3.5f),
            Item("Comentari 3", "Això és un comentari 3.", R.drawable.deadpoolylobezno, 5.0f)
        )
        // Configurar adaptador
        val adapter = ValoracionTituloAdapter(items)
        recyclerView.adapter = adapter
    }
}