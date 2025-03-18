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
import com.example.popview.adapter.ListasPublicasAdapter
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
        // Configurar el listener para las insets del sistema
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
                    // Si el campo de búsqueda está vacío, vuelve a cargar todas las listas
                    buscarListas("")
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        // Cargar todas las listas públicas al inicio (sin filtro)
        buscarListas("")
    }
    private fun buscarListas(query: String) {
        lifecycleScope.launch {
            try {
                val response = if (query.isEmpty()) {
                    popViewService.getAllLlistesPublicas()
                } else {
                    popViewService.buscarListasPublicas(query)
                }
                if (response.isSuccessful) {
                    val listasPublicas = response.body() ?: emptyList()
                    Log.d("BuscarListasActivity", "Listas recibidas: $listasPublicas")
                    runOnUiThread {
                        listaDeListas.clear()
                        listaDeListas.addAll(listasPublicas)
                        listasAdapter.notifyDataSetChanged()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("BuscarListasActivity", "Error al obtener listas públicas: ${response.message()}")
                    Log.e("BuscarListasActivity", "Cuerpo del error: $errorBody")
                    runOnUiThread {
                        Toast.makeText(
                            this@BuscarListasActivity,
                            "Error al buscar listas: ${response.message()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
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