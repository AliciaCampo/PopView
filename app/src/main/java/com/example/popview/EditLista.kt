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
import com.example.popview.PeliculasAdapter

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

        // Prellenar con los datos existentes
        if (listaData != null) {
            val partes = listaData.split(", ")
            val titulo = partes[0]  // El título de la lista (por ejemplo: "Serie: S_ries per veure")
            val descripcion = partes[1]  // La descripción
            val privacidad = partes[2]  // La privacidad (Privada/Pública)
            editTextTitulo.setText(titulo)  // Establecer el título en el EditText
            editTextDescripcion.setText(descripcion)  // Establecer la descripción
            switchPrivada.isChecked = privacidad == "Privada"  // Establecer el estado del switch
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
            Toast.makeText(this, "Lista guardada: $listaTitulo, $listaDescripcion, Privacidad: $esPrivada", Toast.LENGTH_SHORT).show()
            finish()  // Finalizar la actividad y regresar
        }
    }
}
