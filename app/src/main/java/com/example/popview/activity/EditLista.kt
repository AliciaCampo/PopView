package com.example.popview.activity


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.data.Lista
import com.example.popview.adapter.PeliculasAdapter
import com.example.popview.R

class EditLista : AppCompatActivity() {
    private val peliculasList = mutableListOf<String>() // Lista para almacenar títulos de películas
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PeliculasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lista)

        val editTextTitulo = findViewById<EditText>(R.id.editTextTitulo)
        val editTextDescripcion = findViewById<EditText>(R.id.editTextDescripcion)
        val switchPrivada = findViewById<Switch>(R.id.switchPrivada)

        // Recibir el objeto Lista
        val listaData = intent.getSerializableExtra("listaData") as? Lista
        if (listaData != null) {
            // Actualizar los campos con la información de la lista
            editTextTitulo.setText(listaData.titulo)
            editTextDescripcion.setText(listaData.descripcion)
            switchPrivada.isChecked = listaData.esPrivada
            updateDescripcionVisibility(listaData.esPrivada, editTextDescripcion)
        } else {
            Toast.makeText(this, "No s'han rebut les dades de la llista.", Toast.LENGTH_SHORT).show()
        }

        switchPrivada.setOnCheckedChangeListener { _, isChecked ->
            updateDescripcionVisibility(isChecked, editTextDescripcion)
        }

        // Configuración del RecyclerView para películas
        recyclerView = findViewById(R.id.recyclerViewPeliculas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PeliculasAdapter(peliculasList)
        recyclerView.adapter = adapter

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        btnGuardar.setOnClickListener {
            val listaTitulo = editTextTitulo.text.toString()
            val listaDescripcion = editTextDescripcion.text.toString()
            val esPrivada = switchPrivada.isChecked

            Toast.makeText(
                this,
                "Llista guardada: $listaTitulo, $listaDescripcion, Privacitat: ${if (esPrivada) "Privada" else "Pública"}",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }

        val btnEliminar = findViewById<ImageView>(R.id.btnEliminar)
        btnEliminar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmació")
            builder.setMessage("Segur que vols esborrar la llista?")
            builder.setPositiveButton("Confirmar") { dialog, _ ->
                val intent = Intent()
                intent.putExtra("eliminarLista", listaData) // Enviar la lista eliminada
                setResult(RESULT_OK, intent)
                finish()
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel·lar") { dialog, _ ->
                dialog.dismiss()
            }
            builder.create().show()
        }
        val btnAñadirPelicula = findViewById<Button>(R.id.btnAñadirPelicula)
        val editTextPelicula = findViewById<EditText>(R.id.editTextPelicula)
        btnAñadirPelicula.setOnClickListener {
            val peliculaTitulo = editTextPelicula.text.toString().trim()
            if (peliculaTitulo.isNotEmpty()) {
                peliculasList.add(peliculaTitulo)
                adapter.notifyDataSetChanged()
                editTextPelicula.text.clear()
                Toast.makeText(this, "Títol afegit: $peliculaTitulo", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Siusplau, ingresa un títol", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun updateDescripcionVisibility(isPrivada: Boolean, descripcionField: EditText) {
        if (isPrivada) {
            descripcionField.visibility = EditText.GONE
        } else {
            descripcionField.visibility = EditText.GONE // Mostrar el campo de descripción
        }
    }
}
