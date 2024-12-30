package com.example.popview


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BuscarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imageButtonWadeYLogan = findViewById<ImageButton>(R.id.deadpoolylobezno)
        imageButtonWadeYLogan.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@BuscarActivity,
                ValoracionTituloActivity::class.java
            )
            startActivity(intent)
        })
        val imageFiltro = findViewById<ImageView>(R.id.imageFiltro)
        imageFiltro.setOnClickListener {
            // Opciones de filtro
            val filtros = arrayOf("+12", "+16", "+18", "Serie", "Pelicula", "Acción", "Fantasía", "Superhéroes", "Comedia", "Director")
            // Crear diálogo
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Selecciona un o més filtres")
            builder.setItems(filtros) { _, which ->
                // Por ahora solo es visual, no hace nada al seleccionarlo
                // Puedes mostrar un Toast para indicar la selección si deseas
            }
            val dialog = builder.create()
            dialog.show()
    }
}}