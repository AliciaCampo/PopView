package com.example.popview.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.adapter.ImageAdapter
import com.example.popview.data.ImageItem
import com.example.popview.data.Titulo
import com.example.popview.interficie.FilterListener
import com.example.popview.itemdecoration.ItemSpacingDecoration
import com.example.popview.service.PopViewAPI
import com.example.popview.service.PopViewService
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(), FilterListener {
    private lateinit var popViewService: PopViewService
    private var allTitles: List<Titulo> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        popViewService = PopViewAPI().API()

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
            (selectedGenres.isEmpty() || selectedGenres.contains(titulo.genero)) &&
                    (selectedPlatforms.isEmpty() || titulo.platforms.any { it in selectedPlatforms })
        }

        setupRecyclerViews(filteredTitles)
    }

    private fun setupRecyclerViews(titles: List<Titulo>) {
        val recomendaciones = titles.filter { it.nombre in listOf("Deadpool y Lobezno", "Del Reves 2", "Beetlejuice 2", "Miercoles", "Juego del Calamar") }
        val peliculasPopulares = titles.filter { it.nombre in listOf("Beetlejuice 2", "Joker 2", "Venom 3", "Robot Salvaje", "Deadpool y Lobezno") }
        val seriesPopulares = titles.filter { it.nombre in listOf("Respira", "Miercoles", "Juego del Calamar", "Casa de Papel", "Cobra Kai") }

        configureRecyclerView(
            recyclerViewId = R.id.recyclerViewRecomancions,
            data = recomendaciones.map { ImageItem(it.imagen, it) },
            spacing = 16
        )

        configureRecyclerView(
            recyclerViewId = R.id.recyclerViewImagePelisPop,
            data = peliculasPopulares.map { ImageItem(it.imagen, it) },
            spacing = 16
        )

        configureRecyclerView(
            recyclerViewId = R.id.recyclerViewSeriesPop,
            data = seriesPopulares.map { ImageItem(it.imagen, it) },
            spacing = 16
        )
    }

    private fun configureRecyclerView(
        recyclerViewId: Int,
        data: List<ImageItem>,
        spacing: Int
    ) {
        val recyclerView: RecyclerView = findViewById(recyclerViewId)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ImageAdapter(data)
        recyclerView.addItemDecoration(ItemSpacingDecoration(spacing))
    }
}
