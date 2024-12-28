package com.example.popview

import PeliculasAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EditLista : AppCompatActivity() {
    private val peliculasList = mutableListOf<String>() // Lista para almacenar títulos de películas
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PeliculasAdapter
    private var listaTitulo: String? = null  // Variable para guardar el título de la lista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lista)
        val listaData = intent.getStringExtra("listaData")
        val editTextTitulo = findViewById<EditText>(R.id.editTextTitulo)
        val editTextDescripcion = findViewById<EditText>(R.id.editTextDescripcion)
        val switchPrivada = findViewById<Switch>(R.id.switchPrivada)
        val editTextPelicula = findViewById<EditText>(R.id.editTextPelicula)
        val btnAñadirPelicula = findViewById<Button>(R.id.btnAñadirPelicula)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val tituloLista = intent.getStringExtra("tituloLista")
        if (tituloLista != null) {
            findViewById<TextView>(R.id.textTitulo).text = tituloLista
        }
        recyclerView = findViewById(R.id.recyclerViewPeliculas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PeliculasAdapter(peliculasList)
        recyclerView.adapter = adapter
        if (listaData != null) {
            val partes = listaData.split(", ")
            listaTitulo = partes.getOrNull(0) ?: ""
            val descripcion = partes.getOrNull(1) ?: ""
            val privacidad = partes.getOrNull(2) ?: "Pública"
            editTextTitulo.setText(listaTitulo)
            editTextDescripcion.setText(descripcion)
            switchPrivada.isChecked = privacidad == "Privada"
            updateDescripcionVisibility(switchPrivada.isChecked, editTextDescripcion)
            val listas = loadLists(this)
            val lista = listas.find { it.titulo == listaTitulo }
            if (lista != null) {
                peliculasList.clear()
                peliculasList.addAll(lista.peliculas)  // Cargar las películas
                adapter.notifyDataSetChanged()
            }
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
            val listaModificada = Lista(
                listaTitulo, listaDescripcion, esPrivada, peliculasList
            )
            saveList(this, listaModificada)  // Guardar la lista modificada
            val intent = Intent()
            intent.putExtra("listaModificada", listaModificada)  // Pasar la lista modificada
            setResult(RESULT_OK, intent)
            finish()
        }
        val btnEliminar = findViewById<ImageButton>(R.id.btnEliminar)
        btnEliminar.setOnClickListener {
            mostrarDialogoConfirmacion(tituloLista)
        }
    }
    private fun mostrarDialogoConfirmacion(tituloLista: String?) {
        if (tituloLista == null) return
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Quieres borrar la lista \"$tituloLista\"?")
        builder.setPositiveButton("Confirmar") { _, _ ->
            eliminarLista(tituloLista)
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
    private fun eliminarLista(tituloLista: String) {
        val intent = Intent()
        intent.putExtra("listaAEliminar", tituloLista)
        setResult(RESULT_OK, intent)
        finish() // Cierra la actividad
    }
    private fun updateDescripcionVisibility(isPrivada: Boolean, descripcionField: EditText) {
        if (isPrivada) {
            descripcionField.visibility = EditText.GONE
        } else {
            descripcionField.visibility = EditText.VISIBLE
        }
    }
}