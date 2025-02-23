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
        val recomendaciones = titles.filter { it.nombre in listOf(
            "Star Wars: Una nova esperança", "Star Wars: L'Imperi contraataca", "Star Wars: El retorn del Jedi",
            "Star Wars: La amenaça fantasma", "Star Wars: L'atac dels clons", "Star Wars: La venjança dels Sith",
            "Star Wars: El despertar de la força", "Star Wars: Els últims Jedi", "Star Wars: L'ascens de Skywalker",
            "Harry Potter i la pedra filosofal", "Harry Potter i la cambra secreta",
            "Harry Potter i el presoner d'Azkaban", "Harry Potter i el calze de foc",
            "Harry Potter i l'orde del Fènix", "Harry Potter i el princep mestís",
            "Harry Potter i les relíquies de la mort: Part 1", "Harry Potter i les relíquies de la mort: Part 2",
            "Spider-Man", "Spider-Man 2", "Spider-Man 3", "The Amazing Spider-Man", "The Amazing Spider-Man 2",
            "Spider-Man: Homecoming", "Spider-Man: Far From Home", "Spider-Man: No Way Home",
            "Deadpool", "Deadpool 2", "Fast & Furious", "Fast & Furious 2", "Fast & Furious 3: Tòquio Drift",
            "Fast & Furious 4", "Fast & Furious 5", "Fast & Furious 6", "Fast & Furious 7", "Fast & Furious 8",
            "Fast & Furious 9", "El senyor dels anells: La comunitat de l'anell",
            "El senyor dels anells: Les dues torres", "El senyor dels anells: El retorn del rei",
            "El Hobbit: Un viatge inesperat", "El Hobbit: La desolació de Smaug",
            "El Hobbit: La batalla dels cinc exèrcits", "Barbie", "Oppenheimer", "Titanic", "E.T.", "Els 100",
            "Matilda", "Temps"
        )}

        val peliculasPopulares = titles.filter { it.nombre in listOf(
            "Star Wars: Una nova esperança", "Star Wars: L'Imperi contraataca", "Star Wars: El retorn del Jedi",
            "Star Wars: La amenaça fantasma", "Star Wars: L'atac dels clons", "Star Wars: La venjança dels Sith",
            "Star Wars: El despertar de la força", "Star Wars: Els últims Jedi", "Star Wars: L'ascens de Skywalker",
            "Harry Potter i la pedra filosofal", "Harry Potter i la cambra secreta",
            "Harry Potter i el presoner d'Azkaban", "Harry Potter i el calze de foc",
            "Harry Potter i l'orde del Fènix", "Harry Potter i el princep mestís",
            "Harry Potter i les relíquies de la mort: Part 1", "Harry Potter i les relíquies de la mort: Part 2",
            "Spider-Man", "Spider-Man 2", "Spider-Man 3", "The Amazing Spider-Man", "The Amazing Spider-Man 2",
            "Spider-Man: Homecoming", "Spider-Man: Far From Home", "Spider-Man: No Way Home",
            "Deadpool", "Deadpool 2", "Fast & Furious", "Fast & Furious 2", "Fast & Furious 3: Tòquio Drift",
            "Fast & Furious 4", "Fast & Furious 5", "Fast & Furious 6", "Fast & Furious 7", "Fast & Furious 8",
            "Fast & Furious 9", "El senyor dels anells: La comunitat de l'anell",
            "El senyor dels anells: Les dues torres", "El senyor dels anells: El retorn del rei",
            "El Hobbit: Un viatge inesperat", "El Hobbit: La desolació de Smaug",
            "El Hobbit: La batalla dels cinc exèrcits", "Oppenheimer", "Titanic", "E.T.", "Els 100",
            "Matilda"
        )}

        val seriesPopulares = titles.filter { it.nombre in listOf(
            "Friends", "Grey’s Anatomy", "The Big Bang Theory", "Merlí",
            "Pulseres vermelles", "Mic", "La que se avecina", "Aquí no hi ha qui visqui",
            "Començo a comptar", "The Walking Dead", "Breaking Bad", "Peaky Blinders",
            "Shameless", "Els Simpson", "Arcane", "American Horror Story",
            "Laia (pel·lícula de Netflix)", "The rain", "Benvinguts a Eden", "SCREAM",
            "Enola Holmes", "La teoria del tot", "La maledicció de Bly Manor",
            "Vis a Vis", "Les noies del cable", "Heartstopper", "La primera mort",
            "La carrer del terror", "Destí la saga Winx", "Gambit de dama",
            "The umbrella academy", "Atípic", "Desencant", "La monja guerrera",
            "Elite", "Paquita Salas", "Ni una més", "Dahmer", "Joves i bruixes",
            "Nina russa", "Tallant per la línia de punts", "Invencible"
        )}

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
