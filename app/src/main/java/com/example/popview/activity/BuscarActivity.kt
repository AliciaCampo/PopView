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
import com.example.popview.`object`.AppContext
import com.example.popview.R
import com.example.popview.data.Titulo
import com.example.popview.adapter.AdaptadorImagenes
import com.example.popview.service.PopViewAPI
import com.example.popview.service.PopViewService
import kotlinx.coroutines.launch


class BuscarActivity : AppCompatActivity() {
    private lateinit var popViewService: PopViewService
    private lateinit var allTitles: List<Titulo>
    private val buscarQuery = intent.getStringExtra("SEARCH_QUERY") ?: ""
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
            try{
                val allTitles= popViewService.getAllTitols()
                val filteredTitles = allTitles.filter { it.nombre.contains(buscarQuery, ignoreCase = true) }
                actualizaRecyclerView(filteredTitles)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        val imageFiltro = findViewById<ImageView>(R.id.imageFiltro)
        // Lista de filtros y su estado seleccionado
        val filtros = arrayOf("+12", "+16", "+18", "Sèrie", "Pel·lícula", "Acció", "Fantasía", "Superherois", "Comèdia")
        val seleccionados = BooleanArray(filtros.size) { false } // Estado de selección inicial: ninguno seleccionado
        imageFiltro.setOnClickListener {
            // Crear el diálogo con opciones múltiples seleccionables
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Selecciona un o més filtres")
            builder.setMultiChoiceItems(filtros, seleccionados) { _, which, isChecked ->
                // Actualizar el estado de selección
                seleccionados[which] = isChecked
            }
            builder.setPositiveButton("Acceptar") { dialog, _ ->
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel·lar") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
    private fun actualizaRecyclerView(titles: List<Titulo>){
        val recyclerViewContent = findViewById<RecyclerView>(R.id.recyclerViewContent)
        recyclerViewContent.layoutManager = GridLayoutManager(this, 2)
        val adaptador = AdaptadorImagenes(titles) { titulo ->
            val intent = Intent(this@BuscarActivity, ValoracionTituloActivity::class.java)
            intent.putExtra("titulo", titulo)
            startActivity(intent)
        }
        recyclerViewContent.adapter = adaptador
    }
    private fun aplicarFiltros(seleccionados: BooleanArray) {
        val filteredTitles = allTitles.filter { titulo ->
            val busquedaSeleccionada = titulo.nombre.contains(buscarQuery, ignoreCase = true)
            val filtroSeleccionado = (!seleccionados.any { it } || // Si ningún filtro está seleccionado, mostrar todos los títulos que coinciden con la búsqueda
                    (seleccionados[0] && titulo.edadRecomendada >= 12) ||
                    (seleccionados[1] && titulo.edadRecomendada >= 16) ||
                    (seleccionados[2] && titulo.edadRecomendada >= 18) ||
                    (seleccionados[3] && titulo.genero == "Sèrie") ||
                    (seleccionados[4] && titulo.genero == "Pel·lícula") ||
                    (seleccionados[5] && titulo.genero == "Acció") ||
                    (seleccionados[6] && titulo.genero == "Fantasía") ||
                    (seleccionados[7] && titulo.genero == "Superherois") ||
                    (seleccionados[8] && titulo.genero == "Comèdia"))
            busquedaSeleccionada && filtroSeleccionado
        }
        actualizaRecyclerView(filteredTitles)
    }
}