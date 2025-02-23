package com.example.popview.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.adapter.RatingAdapter
import com.example.popview.data.Item
import com.example.popview.service.PopViewAPI
import com.example.popview.service.PopViewService
import kotlinx.coroutines.launch

class RatingActivity : AppCompatActivity() {

    private lateinit var popViewService: PopViewService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        popViewService = PopViewAPI().API()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // Dos columnas

        lifecycleScope.launch {
            try {
                val allTitles = popViewService.getAllTitols()
                val topTitles = allTitles.sortedByDescending { it.rating }.take(5)
                val ratingItems = topTitles.mapIndexed { index, titulo ->
                    Item(
                        title = "${index + 1}. ${titulo.nombre}",
                        imageUrl = titulo.imagen,
                        description = titulo.description,
                        rating = titulo.rating
                    )
                }
                recyclerView.adapter = RatingAdapter(ratingItems)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}