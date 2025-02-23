package com.example.popview.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.adapter.ListasAdapter
import com.example.popview.data.Lista
import com.example.popview.service.PopViewAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsuarioActivity : AppCompatActivity() {
    private val listaDeListas = mutableListOf<Lista>()
    private lateinit var listasAdapter: ListasAdapter
    private val popViewService = PopViewAPI().API()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewListas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Pasar el manejador de clics al adaptador
        listasAdapter = ListasAdapter(listaDeListas) { lista ->
            // Manejar el clic en un elemento de la lista
            val intent = Intent(this, EditLista::class.java)
            intent.putExtra("lista", lista)
            startActivity(intent)
        }
        recyclerView.adapter = listasAdapter
        val btnCrearLista = findViewById<Button>(R.id.btnCrearLista)
        btnCrearLista.setOnClickListener {
            val intent = Intent(this, CrearListaActivity::class.java)
            startActivityForResult(intent, CREAR_LISTA_REQUEST_CODE)
        }
        // Cargar las listas del usuario desde el servidor
        cargarListasUsuario()
    }
    private fun cargarListasUsuario() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val listas = popViewService.getAllLlistes()
                runOnUiThread {
                    listaDeListas.clear()
                    listaDeListas.addAll(listas)
                    listasAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREAR_LISTA_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.getSerializableExtra("nuevaLista")?.let {
                val nuevaLista = it as Lista
                listaDeListas.add(nuevaLista)
                listasAdapter.notifyItemInserted(listaDeListas.size - 1)
            }
        }
    }

    companion object {
        const val CREAR_LISTA_REQUEST_CODE = 1
    }
}
