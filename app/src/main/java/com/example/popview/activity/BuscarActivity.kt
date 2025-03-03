package com.example.popview.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.data.Titulo
import com.example.popview.adapter.AdaptadorImagenes
import com.example.popview.service.PopViewAPI
import com.example.popview.service.PopViewService
import kotlinx.coroutines.launch
import kotlin.math.min

class BuscarActivity : AppCompatActivity() {
    private lateinit var popViewService: PopViewService
    private lateinit var allTitles: List<Titulo>
    private val buscarQuery by lazy { intent.getStringExtra("SEARCH_QUERY") ?: "" }
    private lateinit var seleccionados: BooleanArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        popViewService = PopViewAPI().API()
        lifecycleScope.launch {
            try {
                allTitles = popViewService.getAllTitols()
                aplicarFiltros()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val imageFiltro = findViewById<ImageView>(R.id.imageFiltro)
        val filtros = arrayOf("+12", "+16", "+18", "Sèrie", "Pel·lícula", "Acció", "Fantasía", "Superherois", "Comèdia")
        seleccionados = BooleanArray(filtros.size) { false }

        imageFiltro.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Selecciona un o més filtres")
                .setMultiChoiceItems(filtros, seleccionados) { _, which, isChecked ->
                    seleccionados[which] = isChecked
                }
                .setPositiveButton("Acceptar") { _, _ -> aplicarFiltros() }
                .setNegativeButton("Cancel·lar") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private fun actualizaRecyclerView(titles: List<Titulo>) {
        val recyclerViewContent = findViewById<RecyclerView>(R.id.recyclerViewContent)
        recyclerViewContent.layoutManager = GridLayoutManager(this, 2)
        val adaptador = AdaptadorImagenes(titles) // Ya no recibe un callback
        recyclerViewContent.adapter = adaptador
    }



    private fun aplicarFiltros() {
        val filteredTitles = allTitles.filter { titulo ->
            // Aplicamos la búsqueda difusa con similitud mayor al 60%
            val similitud = similarity(titulo.nombre, buscarQuery)
            val busquedaSeleccionada = similitud >= 60.0

            // Aplicación de filtros
            val filtroSeleccionado = (!seleccionados.any { it } ||
                    (seleccionados[0] && (titulo.edadRecomendada ?: 0) >= 12) ||
                    (seleccionados[1] && (titulo.edadRecomendada ?: 0) >= 16) ||
                    (seleccionados[2] && (titulo.edadRecomendada ?: 0) >= 18) ||
                    (seleccionados[3] && titulo.genero == "Sèrie") ||
                    (seleccionados[4] && titulo.genero == "Pel·lícula") ||
                    (seleccionados[5] && titulo.genero == "Acció") ||
                    (seleccionados[6] && titulo.genero == "Fantasía") ||
                    (seleccionados[7] && titulo.genero == "Superherois") ||
                    (seleccionados[8] && titulo.genero == "Comèdia"))

            // Se incluyen solo los títulos que pasen ambos filtros
            busquedaSeleccionada && filtroSeleccionado
        }

        actualizaRecyclerView(filteredTitles)
    }

    // Función para calcular la distancia de Levenshtein
    private fun levenshteinDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }

        for (i in s1.indices) dp[i][0] = i
        for (j in s2.indices) dp[0][j] = j

        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                dp[i][j] = if (s1[i - 1] == s2[j - 1]) {
                    dp[i - 1][j - 1]
                } else {
                    min(min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1
                }
            }
        }

        return dp[s1.length][s2.length]
    }

    // Función para calcular la similitud (0 a 100%)
    private fun similarity(s1: String, s2: String): Double {
        val maxLength = maxOf(s1.length, s2.length)
        return if (maxLength > 0) {
            (1.0 - levenshteinDistance(s1.lowercase(), s2.lowercase()).toDouble() / maxLength) * 100
        } else {
            0.0
        }
    }
}
