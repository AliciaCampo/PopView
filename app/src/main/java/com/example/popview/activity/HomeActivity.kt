package com.example.popview.activity

import android.os.Bundle
import android.util.Log
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
                Log.d("HomeActivity", "Títulos recuperados: ${allTitles.size}")  // Verifica el tamaño de los títulos
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
        val peliculasPopulares = titles.filter { it.nombre in listOf(
            "Star Wars: Una nueva esperanza", "Star Wars: El Imperio contraataca", "Star Wars: El retorno del Jedi",
            "Star Wars: La amenaza fantasma", "Star Wars: El ataque de los clones", "Star Wars: La venganza de los Sith",
            "Star Wars: El despertar de la fuerza", "Star Wars: Los últimos Jedi", "Star Wars: El ascenso de Skywalker",
            "Harry Potter y la piedra filosofal", "Harry Potter y la cámara secreta",
            "Harry Potter y el prisionero de Azkaban", "Harry Potter y el cáliz de fuego",
            "Harry Potter y la orden del Fénix", "Harry Potter y el misterio del príncipe",
            "Harry Potter y las reliquias de la muerte: Parte 1", "Harry Potter y las reliquias de la muerte: Parte 2",
            "Spider-Man", "Spider-Man 2", "Spider-Man 3", "The Amazing Spider-Man", "The Amazing Spider-Man 2",
            "Spider-Man: Homecoming", "Spider-Man: Far From Home", "Spider-Man: No Way Home",
            "Deadpool", "Deadpool 2", "Fast & Furious", "Fast & Furious 2", "Fast & Furious 3: Tokyo Drift",
            "Fast & Furious 4", "Fast & Furious 5", "Fast & Furious 6", "Fast & Furious 7", "Fast & Furious 8",
            "Fast & Furious 9", "El señor de los anillos: La comunidad del anillo",
            "El señor de los anillos: Las dos torres", "El señor de los anillos: El retorno del rey",
            "El Hobbit: Un viaje inesperado", "El Hobbit: La desolación de Smaug", "El Hobbit: La batalla de los cinco ejércitos",
            "Oppenheimer", "Titanic", "E.T.", "Los 100", "Matilda"
        )}

        val seriesPopulares = titles.filter { it.nombre in listOf(
            "Friends", "Grey’s Anatomy", "The Big Bang Theory", "Merlí",
            "Pulseres vermelles", "Mic", "La que se avecina", "Aquí no hay quien viva",
            "Començo a comptar", "The Walking Dead", "Breaking Bad", "Peaky Blinders",
            "Shameless", "Los Simpson", "Arcane", "American Horror Story",
            "Laia (película de Netflix)", "The rain", "Benvinguts a Eden", "SCREAM",
            "Enola Holmes", "La teoría del todo", "La maldición de Bly Manor",
            "Vis a Vis", "Las chicas del cable", "Heartstopper", "La primera muerte",
            "La calle del terror", "Destino: La saga Winx", "Gambito de dama",
            "The Umbrella Academy", "Atípico", "Desencantada", "La monja guerrera",
            "Élite", "Paquita Salas", "Ni una más", "Dahmer", "Jóvenes y brujas",
            "Nina rusa", "Cortando por la línea de puntos", "Invencible"
        )}

        configureRecyclerView(
            recyclerViewId = R.id.recyclerViewRecomancions,
            data = peliculasPopulares.map { ImageItem(it.imagen, it) },
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
