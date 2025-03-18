package com.example.popview.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.adapter.ListasAdapter
import com.example.popview.adapter.ListasPublicasAdapter
import com.example.popview.data.Lista
import com.example.popview.data.ListaPublica
import com.example.popview.service.PopViewAPI
import kotlinx.coroutines.launch

class BuscarListasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val listaDeListas = mutableListOf<ListaPublica>()
    private lateinit var listasAdapter: ListasPublicasAdapter
    private val popViewService = PopViewAPI().API()

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
        listasAdapter = ListasPublicasAdapter(listaDeListas) { lista ->
            val intent = Intent(this, DetalleListaPublicaActivity::class.java)
            intent.putExtra("lista", lista) // ListaPublica ya implementa Serializable
            startActivity(intent)
        }

        recyclerView.adapter = listasAdapter

        val editTextBuscar = findViewById<EditText>(R.id.textBuscar)
        editTextBuscar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val buscarQuery = s.toString()
                if (buscarQuery.isNotEmpty()) {
                    buscarListas(buscarQuery)
                } else {
                    // Si el campo de búsqueda está vacío, limpia la lista o carga todas las listas públicas
                    listaDeListas.clear()
                    listasAdapter.notifyDataSetChanged()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Opcional: Cargar todas las listas públicas al inicio (si el backend lo permite)
        buscarListas("")
    }

    private fun buscarListas(query: String) {
        lifecycleScope.launch {
            try {
                val listasPublicas = if (query.isEmpty()) {
                    popViewService.getAllLlistesPublicas()
                } else {
                    popViewService.buscarListasPublicas(query)
                }

                Log.d("BuscarListasActivity", "Listas recibidas: $listasPublicas")

                runOnUiThread {
                    listaDeListas.clear()
                    listaDeListas.addAll(listasPublicas)
                    listasAdapter.notifyDataSetChanged()
                }
            } catch (e: retrofit2.HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("BuscarListasActivity", "Error al obtener listas públicas: ${e.message}")
                Log.e("BuscarListasActivity", "Cuerpo del error: $errorBody")
                runOnUiThread {
                    Toast.makeText(
                        this@BuscarListasActivity,
                        "Error al buscar listas: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("BuscarListasActivity", "Error inesperado: ${e.message}")
                runOnUiThread {
                    Toast.makeText(
                        this@BuscarListasActivity,
                        "Error inesperado: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


}