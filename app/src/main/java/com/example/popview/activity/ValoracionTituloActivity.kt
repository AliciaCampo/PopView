package com.example.popview.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popview.R
import com.example.popview.adapter.ComentariosAdapter
import com.example.popview.data.Comentario
import com.example.popview.data.Titulo
import com.example.popview.fragment.AddTituloLista
import com.example.popview.service.PopViewAPI
import kotlinx.coroutines.launch

class ValoracionTituloActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var comentarioAdapter: ComentariosAdapter
    private val api = PopViewAPI().API()
    private val currentUserId = 5 // Supongamos que el ID del usuario actual es 5
    private var titolId: Int = 0
    private var comentarios: List<Comentario> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_valoracion_titulo)
        val titulo = intent.getSerializableExtra("titulo") as? Titulo
        if (titulo == null) {
            Toast.makeText(this, "Título no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        titolId = intent.getIntExtra("tituloId", 0)
        val imageView = findViewById<ImageView>(R.id.imageContent)
        val textTitle = findViewById<TextView>(R.id.textTitle)
        val textDescription = findViewById<TextView>(R.id.textDescription)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val iconAnadirLista = findViewById<ImageView>(R.id.añadirTittulo)
        iconAnadirLista.setOnClickListener {
            val dialog = AddTituloLista()
            val bundle = Bundle().apply {
                putInt("tituloId", titulo.id)  // Pasa el id del título
            }
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, "AddTituloLista")
        }

        val imageButtonEnrere: ImageButton = findViewById(R.id.imageButtonEnrere)
        imageButtonEnrere.setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val editTextComment = findViewById<EditText>(R.id.editTextComment)
        val buttonSend = findViewById<ImageButton>(R.id.buttonSend)

        buttonSend.setOnClickListener {
            val comentarioText = editTextComment.text.toString()
            val rating = ratingBar.rating
            if (comentarioText.isNotEmpty()) {
                enviarComentario(comentarioText, rating)
            } else {
                Toast.makeText(this, "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
        lifecycleScope.launch {
            try {
                textTitle.text = titulo.nombre
                textDescription.text = titulo.description
                ratingBar.rating = titulo.rating
                Glide.with(this@ValoracionTituloActivity)
                    .load("http://44.205.116.170/${titulo.imagen}")
                    .into(imageView)


                val platformIcons = listOf(
                    findViewById<ImageView>(R.id.platformIcon1),
                    findViewById<ImageView>(R.id.platformIcon2),
                    findViewById<ImageView>(R.id.platformIcon3)
                )

                val platformsList = titulo.platforms.split(", ").map { it.trim() }
                platformIcons.forEachIndexed { index, imageView ->
                    if (index < platformsList.size) {
                        val platform = platformsList[index]
                        imageView.visibility = View.VISIBLE
                        imageView.setImageResource(getPlatformIcon(platform))
                    } else {
                        imageView.visibility = View.GONE
                    }
                }

                // Cargar comentarios
                cargarComentarios()

            } catch (e: Exception) {
                Toast.makeText(this@ValoracionTituloActivity, "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun enviarComentario(comentarioText: String, rating: Float) {
        lifecycleScope.launch {
            try {
                val nuevoComentario = Comentario(usuari_id = currentUserId, comentaris = comentarioText, rating = rating)
                val response = api.agregarComentario(currentUserId, titolId, nuevoComentario)
                if (response.isSuccessful) {
                    Toast.makeText(this@ValoracionTituloActivity, "Comentario enviado", Toast.LENGTH_SHORT).show()
                    cargarComentarios()
                } else {
                    Toast.makeText(this@ValoracionTituloActivity, "Error al enviar comentario", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("ValoracionTituloActivity", "Error al enviar comentario: ${e.message}")
            }
        }
    }
    private fun cargarComentarios() {
        lifecycleScope.launch {
            try {
                val response = api.obtenerTodosLosComentarios(titolId)
                if (response.isSuccessful) {
                    comentarios = response.body() ?: emptyList()
                    Log.d("ValoracionTituloActivity", "Comentarios cargados: ${comentarios.size}")
                } else {
                    Log.e("ValoracionTituloActivity", "Error: ${response.errorBody()?.string()}")
                }
                comentarioAdapter = ComentariosAdapter(
                    comentarios = comentarios,
                    onEditClick = { comentario ->
                        editarComentario(comentario)
                    },
                    onDeleteClick = { comentario ->
                        eliminarComentario(comentario)
                    },
                    currentUserId = currentUserId
                )
                recyclerView.adapter = comentarioAdapter
            } catch (e: Exception) {
                Log.e("ValoracionTituloActivity", "Error al cargar comentarios: ${e.message}")
            }
        }
    }
    private fun editarComentario(comentario: Comentario) {
        lifecycleScope.launch {
            try {
                // Aquí puedes modificar los datos del comentario antes de enviarlo
                val comentarioModificado = comentario.copy(comentaris = "Nuevo texto de comentario")
                val response = api.modificarComentario(currentUserId, titolId, comentarioModificado)
                if (response.isSuccessful) {
                    // Actualizar la lista de comentarios en el adaptador
                    cargarComentarios()
                }
            } catch (e: Exception) {
                // Manejar el error al editar el comentario
            }
        }
    }

    private fun eliminarComentario(comentario: Comentario) {
        lifecycleScope.launch {
            try {
                val response = api.eliminarComentario(currentUserId, titolId)
                if (response.isSuccessful) {
                    // Actualizar la lista de comentarios en el adaptador
                    cargarComentarios()
                }
            } catch (e: Exception) {
                // Manejar el error al eliminar el comentario
            }
        }
    }

    fun getPlatformIcon(platform: String): Int {
        return when (platform) {
            "Netflix" -> R.drawable.logo_netflix
            "Amazon Prime" -> R.drawable.logo_amazon_prime
            "HBO Max" -> R.drawable.logo_hbo
            "Disney+" -> R.drawable.logo_disneyplus
            "Disney" -> R.drawable.logo_disneyplus
            "Movistar" -> R.drawable.logo_movistarplus
            "Jazztel" -> R.drawable.logo_jazzteltv
            "3cat" -> R.drawable.logo_3cat
            else -> R.drawable.logo
        }
    }
}
