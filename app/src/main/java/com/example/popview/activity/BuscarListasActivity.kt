package com.example.popview.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.popview.adapter.ListasAdapter
import com.example.popview.data.Lista
import com.example.popview.service.PopViewAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BuscarListasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val listaDeListas = mutableListOf<Lista>()
    private lateinit var listasAdapter: ListasAdapter
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
        //pasar la información de la lista a una nueva actividad que solo muestra los titulos
        listasAdapter = ListasAdapter(listaDeListas) { lista ->
            val intent = Intent(this, DetalleListaPublicaActivity::class.java)
            intent.putExtra("lista", lista)
            startActivity(intent)
        }
        recyclerView.adapter = listasAdapter
        findViewById<EditText>(R.id.textBuscar).setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                cargarListasPublicas()
                true
            } else {
                false
            }
        }
    }
    private fun cargarListasPublicas() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val listas = popViewService.getAllLlistesPublicas()
                Log.d("BuscarListaActivity", "Listas recibidas: ${listas.size}")
                for (lista in listas) {
                    Log.d("BuscarActivity", "Lista recibida: ${lista.titulo}, ID: ${lista.id}")
                }
                runOnUiThread {
                    listaDeListas.clear()
                    listaDeListas.addAll(listas)
                    Log.d("UsuarioActivity", "Listas actualizadas en el adaptador: ${listaDeListas.size}")
                    listasAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
