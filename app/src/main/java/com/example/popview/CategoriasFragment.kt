package com.example.popview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R

class CategoriesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categorias, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewCategories)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        // Lista de categorías
        val categories = listOf("Acció", "Fantasia", "Superherois", "Comèdia")
        // Configurar adaptador
        val adapter = CategoriesAdapter(categories) { category ->
            // Acción al hacer clic en una categoría
            println("Categoría seleccionada: $category")
        }
        recyclerView.adapter = adapter
        return view
    }
}