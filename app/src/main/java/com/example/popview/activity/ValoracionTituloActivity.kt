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
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popview.PopViewApp
import com.example.popview.R
import com.example.popview.adapter.ComentariosAdapter
import com.example.popview.data.Comentario
import com.example.popview.data.Titulo
import com.example.popview.fragment.AddTituloLista
import com.example.popview.service.PopViewAPI
import com.example.popview.viewmodel.ComentarioViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlin.getValue

class ValoracionTituloActivity : AppCompatActivity() {
    private val viewModel: ComentarioViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var comentarioAdapter: ComentariosAdapter
    private val api = PopViewAPI().API()
    private val currentUserId = 5 // Supongamos que el ID del usuario actual es 5
    private var comentarios: List<Comentario> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_valoracion_titulo)
        // Obtener el objeto Titulo del Intent
        val titulo = intent.getSerializableExtra("titulo") as? Titulo
        if (titulo == null) {
            Toast.makeText(this, "Título no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        val imageView = findViewById<ImageView>(R.id.imageContent)
        val textTitle = findViewById<TextView>(R.id.textTitle)
        val textDescription = findViewById<TextView>(R.id.textDescription)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val iconAnadirLista = findViewById<ImageView>(R.id.añadirTittulo)
        iconAnadirLista.setOnClickListener {
            val dialog = AddTituloLista()
            val bundle = Bundle().apply {
                putInt("tituloId", titulo.id)  // Usar titulo.id directamente
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
        val ratingBarComment = findViewById<RatingBar>(R.id.ratingBarComment)
        val buttonSend = findViewById<ImageButton>(R.id.buttonSend)

        buttonSend.setOnClickListener {
            val comentarioText = editTextComment.text.toString()
            val rating = ratingBarComment.rating
            var hayError = false

            if (comentarioText.isEmpty()) {
                editTextComment.error = "El comentario no puede estar vacío"
                Toast.makeText(this, "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show()
                hayError = true
            }

            if (rating < 0f || rating > 4f) {
                editTextComment.error = "La puntuación debe estar entre 0 y 4"
                Toast.makeText(this, "La puntuación debe estar entre 0 y 4", Toast.LENGTH_SHORT).show()
                hayError = true
            }

            if (hayError) return@setOnClickListener

            enviarComentario(comentarioText, rating, titulo.id)
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
                    findViewById<ImageView>(R.id.platformIcon3),
                )

                val platformsList = titulo.platforms.split(",").map { it.trim() }

                platformIcons.forEachIndexed { index, imageView ->
                    if (index < platformsList.size) {
                        val platform = platformsList[index]
                        imageView.visibility = View.VISIBLE
                        imageView.setImageResource(getPlatformIcon(platform))
                    } else {
                        imageView.visibility = View.GONE
                    }
                }


                // Cargar comentarios usando titulo.id
                cargarComentarios(titulo.id)

            } catch (e: Exception) {
                Toast.makeText(this@ValoracionTituloActivity, "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enviarComentario(comentarioText: String, rating: Float, titolId: Int) {
        lifecycleScope.launch {
            try {
                val nuevoComentario = Comentario(usuari_id = currentUserId, comentaris = comentarioText, rating = rating)
                val response = api.agregarComentario(currentUserId, titolId, nuevoComentario)
                if (response.isSuccessful) {
                    cargarComentarios(titolId)
                    registrarComentarioCreado()
                } else {
                    Toast.makeText(this@ValoracionTituloActivity, "Error al enviar comentario", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("ValoracionTituloActivity", "Error al enviar comentario: ${e.message}")
            }
        }
    }
    private fun cargarComentarios(titolId: Int) {
        lifecycleScope.launch {
            try {
                Log.d("ValoracionTituloActivity", "Solicitando comentarios para titolId: $titolId")
                val response = api.obtenerTodosLosComentarios(titolId)
                if (response.isSuccessful) {
                    comentarios = response.body() ?: emptyList()
                    Log.d("ValoracionTituloActivity", "Comentarios cargados: ${comentarios.size}")
                    comentarios.forEach {
                        Log.d("Comentario", "ID: ${it.id}, Usuari: ${it.usuari_id}, Texto: ${it.comentaris}")
                    }
                } else {
                    Log.e("ValoracionTituloActivity", "Error: ${response.errorBody()?.string()}")
                }
                comentarioAdapter = ComentariosAdapter(
                    comentarios = comentarios.toMutableList(),
                    onEditClick = { comentarios -> mostrarDialogoEdicion(comentarios, titolId) },
                    onDeleteClick = { comentario -> mostrarConfirmacionEliminacion(comentario, titolId) },
                    onSendClick = { comentario -> enviarComentario(comentario.comentaris, comentario.rating, titolId) },
                    currentUserId = currentUserId
                )
                recyclerView.adapter = comentarioAdapter
            } catch (e: Exception) {
                Log.e("ValoracionTituloActivity", "Error al cargar comentarios: ${e.message}")
            }
        }
    }

    private fun editarComentario(comentario: Comentario, titolId: Int) {
        lifecycleScope.launch {
            try {
                val response = api.modificarComentario(currentUserId, titolId, comentario)
                if (response.isSuccessful) {
                    cargarComentarios(titolId)
                } else {
                    val errorMessage = "Error al editar comentario: ${response.code()} ${response.errorBody()?.string()}"
                    Log.e("ValoracionTituloActivity", errorMessage)
                    Toast.makeText(this@ValoracionTituloActivity, "Error al editar comentario", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("ValoracionTituloActivity", "Excepción al editar comentario: ${e.message}")
                Toast.makeText(this@ValoracionTituloActivity, "Error al editar comentario", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun eliminarComentario(comentario: Comentario, titolId: Int) {
        lifecycleScope.launch {
            try {
                val response = api.eliminarComentario(currentUserId, titolId)
                if (response.isSuccessful) {
                    comentarios = comentarios.filter { it.id != comentario.id }
                    comentarioAdapter.updateComentarios(comentarios.toMutableList())  // Actualizar el adaptador
                    cargarComentarios(titolId)  // Recargar comentarios del servidor
                } else {
                    val errorMessage = "Error al eliminar comentario: ${response.code()} ${response.errorBody()?.string()}"
                    Log.e("ValoracionTituloActivity", errorMessage)
                    comentarioAdapter.updateComentarios(comentarios.toMutableList())
                    Toast.makeText(this@ValoracionTituloActivity, "Error al eliminar comentario", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("ValoracionTituloActivity", "Excepción al eliminar comentario: ${e.message}")
                Toast.makeText(this@ValoracionTituloActivity, "Error al eliminar comentario", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun mostrarConfirmacionEliminacion(comentario: Comentario, titolId: Int) {
        if (comentario.usuari_id != currentUserId) {
            Toast.makeText(this, "No puedes eliminar comentarios de otros usuarios", Toast.LENGTH_SHORT).show()
            return
        }
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Estás seguro de que deseas eliminar este comentario?")
            .setCancelable(false)
            .setPositiveButton("Sí") { dialog, id -> eliminarComentario(comentario, titolId); registrarComentarioEliminado() }
            .setNegativeButton("No") { dialog, id -> dialog.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    private fun mostrarDialogoEdicion(comentario: Comentario, titolId: Int) {
        if (comentario.usuari_id != currentUserId) {
            Toast.makeText(this, "No puedes editar comentarios de otros usuarios", Toast.LENGTH_SHORT).show()
            return
        }
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_editar_comentario, null)

        val editText = dialogView.findViewById<EditText>(R.id.editTextComentario)
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.ratingBarComentario)

        editText.setText(comentario.comentaris)
        ratingBar.rating = comentario.rating

        builder.setView(dialogView)
            .setCancelable(false)
            .setPositiveButton("Confirmar") { dialog, id ->
                val nuevoComentarioTexto = editText.text.toString()
                val nuevoRating = ratingBar.rating
                val comentarioModificado = comentario.copy(comentaris = nuevoComentarioTexto, rating = nuevoRating)
                editarComentario(comentarioModificado, titolId)
                registrarComentarioEditado()
            }
            .setNegativeButton("Cancelar") { dialog, id -> dialog.dismiss() }
        val alert = builder.create()
        alert.show()
    }
    private fun registrarComentarioCreado() {
        val db = FirebaseFirestore.getInstance()
        val deviceRef = db.collection("Devices").document(PopViewApp.idDispositiu)
        deviceRef.update("comentarioCreado", FieldValue.increment(1))
            .addOnSuccessListener {
                Log.d("PopViewApp", "Comentario registrado correctamente en Devices")
            }
            .addOnFailureListener { e ->
                Log.e("PopViewApp", "Error al registrar comentario en Devices", e)
            }
    }
    private fun registrarComentarioEditado() {
        val db = FirebaseFirestore.getInstance()
        val deviceRef = db.collection("Devices").document(PopViewApp.idDispositiu)
        deviceRef.update("comentarioEditado", FieldValue.increment(1))
            .addOnSuccessListener {
                Log.d("PopViewApp", "Comentario registrado correctamente en Devices")
            }
            .addOnFailureListener { e ->
                Log.e("PopViewApp", "Error al registrar comentario en Devices", e)
            }
    }
    private fun registrarComentarioEliminado() {
        val db = FirebaseFirestore.getInstance()
        val deviceRef = db.collection("Devices").document(PopViewApp.idDispositiu)
        deviceRef.update("comentarioEliminado", FieldValue.increment(1))
            .addOnSuccessListener {
                Log.d("PopViewApp", "Comentario registrado correctamente en Devices")
            }
            .addOnFailureListener { e ->
                Log.e("PopViewApp", "Error al registrar comentario en Devices", e)
            }
    }
    private fun getPlatformIcon(platform: String): Int {
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