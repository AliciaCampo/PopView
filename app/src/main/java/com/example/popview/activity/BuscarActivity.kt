package com.example.popview.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
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
    private var buscarQuery: String = ""
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

        val editTextBuscar = findViewById<EditText>(R.id.textBuscar)
        editTextBuscar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                buscarQuery = s.toString()
                aplicarFiltros()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val imageFiltro = findViewById<ImageView>(R.id.imageFiltro)
        val filtros = arrayOf("+12", "+16", "+18", "Acción", "Aventura", "Animación", "Fantasía", "Ciencia ficción", "Terror")
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
        val adaptador = AdaptadorImagenes(titles)
        recyclerViewContent.adapter = adaptador
    }

    private fun aplicarFiltros() {
        val queryLower = buscarQuery.lowercase().trim()

        val filteredTitles = allTitles.filter { titulo ->
            val tituloLower = titulo.nombre.lowercase().trim()
            val similarityScore = similarity(tituloLower, queryLower)

            println("Comparando: '$tituloLower' con '$queryLower' → Similaridad: $similarityScore")

            val busquedaSeleccionada = queryLower.isEmpty() || similarityScore >= 60.0

            val filtroSeleccionado = if (seleccionados.any { it }) {
                (seleccionados[0] && (titulo.edadRecomendada ?: 0) >= 12) ||
                        (seleccionados[1] && (titulo.edadRecomendada ?: 0) >= 16) ||
                        (seleccionados[2] && (titulo.edadRecomendada ?: 0) >= 18) ||
                        (seleccionados[3] && titulo.genero == "Acción") ||
                        (seleccionados[4] && titulo.genero == "Aventura") ||
                        (seleccionados[5] && titulo.genero == "Animación") ||
                        (seleccionados[6] && titulo.genero == "Fantasía") ||
                        (seleccionados[7] && titulo.genero == "Ciencia ficción") ||
                        (seleccionados[8] && titulo.genero == "Terror")
            } else {
                true
            }

            busquedaSeleccionada && filtroSeleccionado
        }

        actualizaRecyclerView(filteredTitles)
    }

    private fun similarity(s1: String, s2: String): Double {
        val maxLength = maxOf(s1.length, s2.length)
        if (maxLength == 0) return 100.0  // Si ambas cadenas están vacías, son 100% iguales

        val distance = levenshteinDistance(s1, s2)
        val similarityPercentage = (1.0 - distance.toDouble() / maxLength) * 100

        return similarityPercentage
    }

    private fun levenshteinDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }

        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j

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
}
