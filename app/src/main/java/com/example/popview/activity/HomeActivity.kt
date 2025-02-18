package com.example.popview.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.data.ImageItem
import com.example.popview.itemdecoration.ItemSpacingDecoration
import com.example.popview.R
import com.example.popview.adapter.ImageAdapter

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recomendaciones = listOf(
            ImageItem(R.drawable.deadpoolylobezno),
            ImageItem(R.drawable.delrevesdos),
            ImageItem(R.drawable.beetlejuice2),
            ImageItem(R.drawable.wednesdaymiercoles),
            ImageItem(R.drawable.juegocalamar)
        )

        val peliculasPopulares = listOf(
            ImageItem(R.drawable.beetlejuice2),
            ImageItem(R.drawable.jokerdos),
            ImageItem(R.drawable.venomtres),
            ImageItem(R.drawable.robotsalvaje),
            ImageItem(R.drawable.deadpoolylobezno)
        )

        val seriesPopulares = listOf(
            ImageItem(R.drawable.respira),
            ImageItem(R.drawable.wednesdaymiercoles),
            ImageItem(R.drawable.juegocalamar),
            ImageItem(R.drawable.casapapel),
            ImageItem(R.drawable.cobrakai)
        )

        configureRecyclerView(
            recyclerViewId = R.id.recyclerViewRecomancions,
            data = recomendaciones,
            spacing = 16
        )

        configureRecyclerView(
            recyclerViewId = R.id.recyclerViewImagePelisPop,
            data = peliculasPopulares,
            spacing = 16
        )

        configureRecyclerView(
            recyclerViewId = R.id.recyclerViewSeriesPop,
            data = seriesPopulares,
            spacing = 16
        )
    }

    private fun configureRecyclerView(
        recyclerViewId: Int,
        data: List<ImageItem>,
        spacing: Int
    ) {
        val recyclerView: RecyclerView = findViewById(recyclerViewId)

        // Configurar LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Configurar Adapter
        recyclerView.adapter = ImageAdapter(data)

        // Añadir espaciado entre elementos
        recyclerView.addItemDecoration(ItemSpacingDecoration(spacing))
    }
}
