package com.example.popview

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EditLista : AppCompatActivity() {

    private val peliculasList = mutableListOf<String>() // Lista para almacenar títulos de películas
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PeliculasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_lista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recuperar los datos pasados
        val listaData = intent.getStringExtra("listaData")

        // Referencias de los elementos
        val editTextTitulo = findViewById<EditText>(R.id.editTextTitulo)
        val editTextDescripcion = findViewById<EditText>(R.id.editTextDescripcion)
        val switchPrivada = findViewById<Switch>(R.id.switchPrivada)
        val editTextPelicula = findViewById<EditText>(R.id.editTextPelicula)
        val btnAñadirPelicula = findViewById<Button>(R.id.btnAñadirPelicula)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        // Configuración del RecyclerView
        recyclerView = findViewById(R.id.recyclerViewPeliculas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PeliculasAdapter(peliculasList)
        recyclerView.adapter = adapter

        // Prellenar con los datos existentes y manejar errores de índice
        if (listaData != null) {
            val partes = listaData.split(", ")
            val titulo = partes.getOrNull(0) ?: "" // Usar "" si no hay título
            val descripcion = partes.getOrNull(1) ?: "" // Usar "" si no hay descripción
            val privacidad = partes.getOrNull(2) ?: "Pública" // Asumir "Pública" si no hay dato

            editTextTitulo.setText(titulo)
            editTextDescripcion.setText(descripcion)
            switchPrivada.isChecked = privacidad == "Privada"

            // Actualizar la visibilidad de la descripción en función del estado del Switch
            updateDescripcionVisibility(switchPrivada.isChecked, editTextDescripcion)
        } else {
            Toast.makeText(this, "No se recibieron datos de la lista.", Toast.LENGTH_SHORT).show()
        }

        // Cuando el switch de privacidad cambia
        switchPrivada.setOnCheckedChangeListener { _, isChecked ->
            // Cambiar la visibilidad de la descripción según el estado del switch
            updateDescripcionVisibility(isChecked, editTextDescripcion)
        }

        // Cuando se hace clic en el botón para añadir una película
        btnAñadirPelicula.setOnClickListener {
            val peliculaTitulo = editTextPelicula.text.toString().trim()
            if (peliculaTitulo.isNotEmpty()) {
                peliculasList.add(peliculaTitulo)  // Añadir el título a la lista
                adapter.notifyDataSetChanged()  // Notificar al adaptador para actualizar la vista
                editTextPelicula.text.clear()  // Limpiar el campo de texto
                Toast.makeText(this, "Película añadida: $peliculaTitulo", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, ingresa un título de película", Toast.LENGTH_SHORT).show()
            }
        }

        // Cuando el usuario guarda los cambios
        btnGuardar.setOnClickListener {
            val listaTitulo = editTextTitulo.text.toString()
            val listaDescripcion = editTextDescripcion.text.toString()
            val esPrivada = switchPrivada.isChecked

            // Guardar los datos modificados (puedes guardarlos en una base de datos, SharedPreferences, etc.)
            Toast.makeText(
                this,
                "Lista guardada: $listaTitulo, $listaDescripcion, Privacidad: ${if (esPrivada) "Privada" else "Pública"}",
                Toast.LENGTH_SHORT
            ).show()
            finish()  // Finalizar la actividad y regresar
        }
    }

    // Método para actualizar la visibilidad de la descripción según la privacidad
    private fun updateDescripcionVisibility(isPrivada: Boolean, descripcionField: EditText) {
        if (isPrivada) {
            descripcionField.visibility = EditText.GONE  // Ocultar el campo de descripción
        } else {
            descripcionField.visibility = EditText.VISIBLE  // Mostrar el campo de descripción
        }
    }
}
