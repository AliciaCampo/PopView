package com.example.popview.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.adapter.CategoriesAdapter
import com.example.popview.R
import com.example.popview.interficie.FilterListener

class CategoriesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categorias, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewCategories)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Lista de categorías
        val categories = listOf(
            "Acció", "Aventura", "Animació", "Comèdia", "Ciència ficció",
            "Documental", "Drama", "Fantasia", "Horror", "Històrica",
            "Misteri", "Musical", "Romàntica", "Thriller"
        )

        // Configurar adaptador
        val adapter = CategoriesAdapter(categories) { category ->
            // Acción al hacer clic en una categoría
            (activity as? FilterListener)?.applyFilters(listOf(category), emptyList())
        }
        recyclerView.adapter = adapter
        return view
    }
}
