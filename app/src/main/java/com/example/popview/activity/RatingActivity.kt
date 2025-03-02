package com.example.popview.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.adapter.RatingAdapter
import com.example.popview.data.Item
import com.example.popview.data.Titulo
import com.example.popview.interficie.FilterListener
import com.example.popview.service.PopViewAPI
import com.example.popview.service.PopViewService
import kotlinx.coroutines.launch

class RatingActivity : AppCompatActivity(), FilterListener {

    private lateinit var popViewService: PopViewService
    private lateinit var recyclerView: RecyclerView
    private var allTitles: List<Titulo> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        popViewService = PopViewAPI().API()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // Dos columnas

        lifecycleScope.launch {
            try {
                allTitles = popViewService.getAllTitols()
                applyFilters(emptyList(), emptyList()) // Mostrar todos los títulos inicialmente
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun applyFilters(selectedGenres: List<String>, selectedPlatforms: List<String>) {
        val filteredTitles = allTitles.filter { titulo ->
            val platformsList = titulo.platforms.split(", ").map { it.trim() }
            (selectedGenres.isEmpty() || selectedGenres.contains(titulo.genero)) &&
                    (selectedPlatforms.isEmpty() || platformsList.any { it in selectedPlatforms })
        }

        val topTitles = filteredTitles.sortedByDescending { it.rating }.take(4)
        val ratingItems = topTitles.mapIndexed { index, titulo ->
            Item(
                title = "${index + 1}. ${titulo.nombre}",
                imageUrl = titulo.imagen,
                description = titulo.description,
                rating = titulo.rating
            )
        }
        recyclerView.adapter = RatingAdapter(ratingItems)
    }
}
