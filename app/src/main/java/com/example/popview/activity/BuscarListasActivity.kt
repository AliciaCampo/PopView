package com.example.popview.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.adapter.ContentAdapter
import com.example.popview.service.PopViewAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BuscarListasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContentAdapter
    private val popViewService = PopViewAPI().API()
    private var filtroSeleccionado: String = "todos" // Valor por defecto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_listas2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerViewContent)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ContentAdapter()
        recyclerView.adapter = adapter

        findViewById<ImageView>(R.id.imageFiltro).setOnClickListener {
            mostrarDialogoFiltros()
        }

        findViewById<EditText>(R.id.textBuscar).setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                realizarBusqueda()
                true
            } else {
                false
            }
        }
    }

    private fun mostrarDialogoFiltros() {
        val opciones = arrayOf("Todos", "Usuarios", "Listas Públicas")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona un filtro")
        builder.setItems(opciones) { _, which ->
            filtroSeleccionado = when (which) {
                0 -> "todos"
                1 -> "usuarios"
                2 -> "listas"
                else -> "todos"
            }
            realizarBusqueda()
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun realizarBusqueda() {
        val query = findViewById<EditText>(R.id.textBuscar).text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val resultados = when (filtroSeleccionado) {
                    "usuarios" -> popViewService.buscarUsuarios(query)
                    "listas" -> popViewService.buscarListasPublicas(query)
                    else -> popViewService.buscarTodo(query)
                }
                runOnUiThread {
                    adapter.actualizarDatos(resultados)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
