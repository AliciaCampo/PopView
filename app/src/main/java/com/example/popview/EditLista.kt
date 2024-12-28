package com.example.popview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
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
        setContentView(R.layout.activity_edit_lista)
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
        // Cargar datos si están disponibles
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
        // Cuando el switch de privacidad cambia
        switchPrivada.setOnCheckedChangeListener { _, isChecked ->
            updateDescripcionVisibility(isChecked, editTextDescripcion)
        }
        // Cuando se hace clic en el botón para añadir una película
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
        // Cuando el usuario guarda los cambios
        btnGuardar.setOnClickListener {
            val listaTitulo = editTextTitulo.text.toString()
            val listaDescripcion = editTextDescripcion.text.toString()
            val esPrivada = switchPrivada.isChecked
            val listaModificada = "$listaTitulo, $listaDescripcion, ${if (esPrivada) "Privada" else "Pública"}"

            val intent = Intent()
            intent.putExtra("listaModificada", listaModificada)
            setResult(RESULT_OK, intent)
            finish()
        }
        // Cuando el usuario quiere eliminar la lista
        val btnEliminar = findViewById<ImageButton>(R.id.btnEliminar)
        btnEliminar.setOnClickListener {
            // Aquí puedes poner la lógica para eliminar el ítem o la lista
            Toast.makeText(this, "Lista eliminada", Toast.LENGTH_SHORT).show()
            // Aquí puedes agregar el código que elimina la lista o regresa a la pantalla anterior
        }

    }
    private fun updateDescripcionVisibility(isPrivada: Boolean, descripcionField: EditText) {
        if (isPrivada) {
            descripcionField.visibility = EditText.GONE
        } else {
            descripcionField.visibility = EditText.VISIBLE
        }
    }
}
