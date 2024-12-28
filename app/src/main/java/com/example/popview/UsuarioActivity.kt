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
    private val listas: MutableList<Lista> = mutableListOf() // Lista mutable de listas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewListas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ListasAdapter(listas) { lista ->
            // Acción al hacer clic en una lista
            val intent = Intent(this, EditLista::class.java)
            intent.putExtra("listaData", lista) // Enviar la lista seleccionada
            startActivityForResult(intent, 1) // Usamos startActivityForResult para recibir datos
        }
        recyclerView.adapter = adapter

        // Configurar el botón para crear nueva lista
        val btnCrearLista = findViewById<Button>(R.id.btnCrearLista)
        btnCrearLista.setOnClickListener {
            val intent = Intent(this, CrearListaActivity::class.java)
            startActivityForResult(intent, 1) // Usamos startActivityForResult para recibir datos
        }
    }

    // Recibir los datos de la nueva lista creada o el item eliminado
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                // Si es la creación de nueva lista
                val nuevaLista = data?.getSerializableExtra("nuevaLista") as? Lista
                nuevaLista?.let {
                    listas.add(it) // Añadir la nueva lista a la lista general
                    adapter.notifyItemInserted(listas.size - 1) // Notificar al adaptador
                }
            } else {
                // Si se está eliminando una lista
                val listaEliminada = data?.getSerializableExtra("eliminarLista") as? Lista
                listaEliminada?.let {
                    val posicion = listas.indexOf(it)
                    if (posicion != -1) {
                        listas.removeAt(posicion) // Eliminar la lista de la lista general
                        adapter.notifyItemRemoved(posicion) // Notificar al adaptador que se eliminó el item
                    }
                }
            }
        }
    }
}