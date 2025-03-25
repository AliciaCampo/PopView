package com.example.popview.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.adapter.ListasAdapter
import com.example.popview.data.DataStoreManager
import com.example.popview.data.Lista
import com.example.popview.service.PopViewAPI
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsuarioActivity : AppCompatActivity() {
    private val listaDeListas = mutableListOf<Lista>()
    private lateinit var listasAdapter: ListasAdapter
    private val popViewService = PopViewAPI().API()
    private var usuarioId: Int = 5 // ID estático del usuario
    private lateinit var imagenAvatar: ImageView
    private val updateInterval: Long = 5000 // Intervalo de actualización en milisegundos (5 segundos)
    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            cargarDatosUsuario()
            cargarListasUsuario()
            handler.postDelayed(this, updateInterval)
        }
    }
    private val avatarResources = mapOf(
        "avataruser1" to R.drawable.avataruser1,
        "avataruser2" to R.drawable.avataruser2,
        "avataruser3" to R.drawable.avataruser3,
        "avataruser4" to R.drawable.avataruser4,
        "avataruser5" to R.drawable.avataruser5,
        "avataruser6" to R.drawable.avataruser6
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewListas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Iniciar la actualización periódica
        handler.post(updateRunnable)
        listasAdapter = ListasAdapter(listaDeListas) { lista ->
            val intent = Intent(this, EditLista::class.java)
            intent.putExtra("lista", lista)
            startActivity(intent)
        }
        recyclerView.adapter = listasAdapter
        val btnCrearLista = findViewById<Button>(R.id.btnCrearLista)
        btnCrearLista.setOnClickListener {
            val intent = Intent(this, CrearListaActivity::class.java)
            intent.putExtra("usuarioId", usuarioId)
            startActivityForResult(intent, CREAR_LISTA_REQUEST_CODE)
        }
        val btnBuscarLista = findViewById<Button>(R.id.btnBuscarLista)
        btnBuscarLista.setOnClickListener {
            val intent = Intent(this, BuscarListasActivity::class.java)
            startActivity(intent)
        }
        val userTextView = findViewById<TextView>(R.id.user)
        userTextView.setOnClickListener {
            val intent = Intent(this, DetalleUsuarioActivity::class.java)
            intent.putExtra("usuarioId", usuarioId)
            startActivity(intent)
        }
        imagenAvatar = findViewById(R.id.imagenAvatar)
    }
    private fun cargarListasUsuario() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val listas = popViewService.getLlistesByUsuari(usuarioId)
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
    private fun cargarDatosUsuario() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val usuario = popViewService.getUsuari(usuarioId)
                runOnUiThread {
                    val userTextView = findViewById<TextView>(R.id.user)
                    userTextView.text = usuario.nombre
                    // Cargar la imagen de perfil desde los recursos locales
                    val imagenSeleccionada = usuario.imagen
                    cargarImagenPerfil(imagenSeleccionada)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Log.e("UsuarioActivity", "Error al obtener el usuario: ${e.message}")
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        // Detener la actualización periódica cuando la actividad se destruye
        handler.removeCallbacks(updateRunnable)
    }
    private fun cargarImagenPerfil(nombreImagen: String) {
        val resourceId = avatarResources[nombreImagen] ?: R.drawable.avataruser1
        imagenAvatar.setImageResource(resourceId)
    }
}