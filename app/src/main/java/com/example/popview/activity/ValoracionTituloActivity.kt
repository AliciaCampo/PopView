package com.example.popview.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popview.R
import com.example.popview.adapter.ValoracionTituloAdapter
import com.example.popview.data.Titulo
import com.example.popview.fragment.AddTituloLista
import kotlinx.coroutines.launch

class ValoracionTituloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_valoracion_titulo)

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
                putInt("tituloId", titulo.id)  // Pasa el id del título
            }
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, "AddTituloLista")
        }

        val imageButtonEnrere: ImageButton = findViewById(R.id.imageButtonEnrere)
        imageButtonEnrere.setOnClickListener {
            finish()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurar el botón desplegable
        val toggleRatingButton: TextView = findViewById(R.id.toggleRatingButton)
        val userRatingBar: RatingBar = findViewById(R.id.userRatingBar)

        toggleRatingButton.setOnClickListener {
            if (userRatingBar.visibility == View.VISIBLE) {
                userRatingBar.visibility = View.GONE
            } else {
                userRatingBar.visibility = View.VISIBLE
            }
        }

        // Configurar la sección de comentarios expandible
        val toggleCommentsSection: LinearLayout = findViewById(R.id.toggleCommentsSection)
        val arrowIcon: ImageView = findViewById(R.id.arrowIcon)

        toggleCommentsSection.setOnClickListener {
            if (recyclerView.visibility == View.GONE) {
                recyclerView.visibility = View.VISIBLE
                arrowIcon.setImageResource(android.R.drawable.arrow_up_float)
            } else {
                recyclerView.visibility = View.GONE
                arrowIcon.setImageResource(android.R.drawable.arrow_down_float)
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

                recyclerView.adapter = ValoracionTituloAdapter(titulo.comments ?: emptyList())

            } catch (e: Exception) {
                Toast.makeText(this@ValoracionTituloActivity, "Error al cargar datos", Toast.LENGTH_SHORT).show()
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
