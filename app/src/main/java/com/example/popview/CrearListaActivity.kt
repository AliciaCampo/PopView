package com.example.popview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CrearListaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_lista)
        val editTextTitulo = findViewById<EditText>(R.id.editTextTitulo)
        val switchPrivada = findViewById<Switch>(R.id.switchPrivada)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)


        val imageButtonEnrere: ImageButton = findViewById(R.id.imageButtonEnrere)
        imageButtonEnrere.setOnClickListener {
            // Volver a la pantalla principal
            val intentEnrere = Intent(this, UsuarioActivity::class.java)
            startActivity(intentEnrere)
            finish()
        }
        // Botón de guardar lista
        btnGuardar.setOnClickListener {
            val titulo = editTextTitulo.text.toString()
            val esPrivada = switchPrivada.isChecked
            if (titulo.isNotEmpty()) {
                // Crear el objeto Lista con valores predeterminados para descripcion y peliculas
                val nuevaLista = Lista(titulo, esPrivada = esPrivada)
                // Crear el Intent para enviar la nueva lista
                val intent = Intent()
                intent.putExtra("nuevaLista", nuevaLista) // Pasamos la lista con título y si es privada
                setResult(RESULT_OK, intent)
                finish() // Cerramos la actividad
            } else {
                Toast.makeText(this, "El título no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }
}