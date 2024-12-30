package com.example.popview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class UsuarioActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListasAdapter
    private val listas: MutableList<Lista> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)
        // Inicializamos RecyclerView
        recyclerView = findViewById(R.id.recyclerViewListas)
        // Configuramos el LayoutManager para el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.recyclerViewListas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ListasAdapter(listas) { lista ->
            val intent = Intent(this, EditLista::class.java)
            intent.putExtra("listaData", lista) // Enviar el objeto Lista completo
            startActivityForResult(intent, 1)
        }
        recyclerView.adapter = adapter
        val btnCrearLista = findViewById<Button>(R.id.btnCrearLista)
        btnCrearLista.setOnClickListener {
            val intent = Intent(this, CrearListaActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1 -> {
                    val nuevaLista = data?.getSerializableExtra("nuevaLista") as? Lista
                    nuevaLista?.let {
                        listas.add(it)
                        adapter.notifyItemInserted(listas.size - 1)
                    }
                }
                else -> {
                    val listaEliminada = data?.getSerializableExtra("eliminarLista") as? Lista
                    listaEliminada?.let {
                        val posicion = listas.indexOfFirst { lista -> lista.titulo == it.titulo }
                        if (posicion != -1) {
                            listas.removeAt(posicion)
                            adapter.notifyItemRemoved(posicion)
                        }
                    }
                }
            }
        }
    }

}