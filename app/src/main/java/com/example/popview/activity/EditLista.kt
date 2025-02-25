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
import com.example.popview.data.Titulo
import com.example.popview.adapter.PeliculasAdapter
import com.example.popview.R
import com.example.popview.service.PopViewAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditLista : AppCompatActivity() {
    private val peliculasList = mutableListOf<Titulo>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PeliculasAdapter
    private val popViewService = PopViewAPI().API()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lista)
        val editTextTitulo = findViewById<EditText>(R.id.editTextTitulo)
        val editTextDescripcion = findViewById<EditText>(R.id.editTextDescripcion)
        val switchPrivada = findViewById<Switch>(R.id.switchPrivada)

        val listaData = intent.getSerializableExtra("lista") as? Lista
        if (listaData != null) {
            editTextTitulo.setText(listaData.titulo)
            editTextDescripcion.setText(listaData.descripcion)
            switchPrivada.isChecked = listaData.esPrivada
            updateDescripcionVisibility(listaData.esPrivada, editTextDescripcion)
            listaData.titulos?.let {
                peliculasList.addAll(it)
            }
        } else {
            Toast.makeText(this, "No s'han rebut les dades de la llista.", Toast.LENGTH_SHORT).show()
        }

        switchPrivada.setOnCheckedChangeListener { _, isChecked ->
            updateDescripcionVisibility(isChecked, editTextDescripcion)
        }

        recyclerView = findViewById(R.id.recyclerViewPeliculas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PeliculasAdapter(peliculasList) { titulo ->
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmació")
            builder.setMessage("Segur que vols eliminar aquest títol de la llista?")
            builder.setPositiveButton("Confirmar") { dialog, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        listaData?.let { lista ->
                            popViewService.deleteTituloFromLista(lista.id, titulo.id)
                            runOnUiThread {
                                peliculasList.remove(titulo)
                                adapter.notifyDataSetChanged()
                                Toast.makeText(this@EditLista, "Títol eliminat de la llista.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel·lar") { dialog, _ ->
                dialog.dismiss()
            }
            builder.create().show()
        }
        recyclerView.adapter = adapter

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        btnGuardar.setOnClickListener {
            val listaTitulo = editTextTitulo.text.toString()
            val listaDescripcion = editTextDescripcion.text.toString()
            val esPrivada = switchPrivada.isChecked

            listaData?.let { lista ->
                val updatedLista = lista.copy(
                    titulo = listaTitulo,
                    descripcion = listaDescripcion,
                    esPrivada = esPrivada,
                    titulos = peliculasList
                )

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        popViewService.createLista(updatedLista)
                        runOnUiThread {
                            Toast.makeText(this@EditLista, "Llista desada amb èxit.", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

        val btnEliminar = findViewById<ImageView>(R.id.btnEliminar)
        btnEliminar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmació")
            builder.setMessage("Segur que vols eliminar la llista?")
            builder.setPositiveButton("Confirmar") { dialog, _ ->
                listaData?.let { lista ->
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            popViewService.deleteLista(lista.id)
                            runOnUiThread {
                                val intent = Intent()
                                intent.putExtra("eliminarLista", lista)
                                setResult(RESULT_OK, intent)
                                finish()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
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
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val titulos = popViewService.getAllTitols()
                        val tituloExistente = titulos.find { it.nombre == peliculaTitulo }
                        if (tituloExistente != null) {
                            runOnUiThread {
                                peliculasList.add(tituloExistente)
                                adapter.notifyDataSetChanged()
                                editTextPelicula.text.clear()
                                Toast.makeText(this@EditLista, "Títol afegit: $peliculaTitulo", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(this@EditLista, "El títol no existeix al servidor.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } else {
                Toast.makeText(this, "Si us plau, introdueix un títol.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateDescripcionVisibility(isPrivada: Boolean, descripcionField: EditText) {
        descripcionField.visibility = if (isPrivada) EditText.GONE else EditText.VISIBLE
    }
}
