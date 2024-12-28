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
        val listaData = intent.getStringExtra("listaData")
        val editTextTitulo = findViewById<EditText>(R.id.editTextTitulo)
        val editTextDescripcion = findViewById<EditText>(R.id.editTextDescripcion)
        val switchPrivada = findViewById<Switch>(R.id.switchPrivada)
        val editTextPelicula = findViewById<EditText>(R.id.editTextPelicula)
        val btnAñadirPelicula = findViewById<Button>(R.id.btnAñadirPelicula)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        recyclerView = findViewById(R.id.recyclerViewPeliculas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PeliculasAdapter(peliculasList)
        recyclerView.adapter = adapter
        if (listaData != null) {
            val partes = listaData.split(", ")
            val titulo = partes.getOrNull(0) ?: ""
            val descripcion = partes.getOrNull(1) ?: ""
            val privacidad = partes.getOrNull(2) ?: "Pública"
            editTextTitulo.setText(titulo)
            editTextDescripcion.setText(descripcion)
            switchPrivada.isChecked = privacidad == "Privada"
            updateDescripcionVisibility(switchPrivada.isChecked, editTextDescripcion)
        } else {
            Toast.makeText(this, "No se recibieron datos de la lista.", Toast.LENGTH_SHORT).show()
        }
        switchPrivada.setOnCheckedChangeListener { _, isChecked ->
            updateDescripcionVisibility(isChecked, editTextDescripcion)
        }
        btnAñadirPelicula.setOnClickListener {
            val peliculaTitulo = editTextPelicula.text.toString().trim()
            if (peliculaTitulo.isNotEmpty()) {
                peliculasList.add(peliculaTitulo)
                adapter.notifyDataSetChanged()
                editTextPelicula.text.clear()
                Toast.makeText(this, "Película añadida: $peliculaTitulo", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, ingresa un título de película", Toast.LENGTH_SHORT).show()
            }
        }
        btnGuardar.setOnClickListener {
            val listaTitulo = editTextTitulo.text.toString()
            val listaDescripcion = editTextDescripcion.text.toString()
            val esPrivada = switchPrivada.isChecked
            Toast.makeText(
                this,
                "Lista guardada: $listaTitulo, $listaDescripcion, Privacidad: ${if (esPrivada) "Privada" else "Pública"}",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
    private fun updateDescripcionVisibility(isPrivada: Boolean, descripcionField: EditText) {
        if (isPrivada) {
            descripcionField.visibility = EditText.GONE
        } else {
            descripcionField.visibility = EditText.VISIBLE  // Mostrar el campo de descripción
        }
    }
}