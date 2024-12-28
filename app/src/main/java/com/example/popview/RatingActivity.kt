package com.example.popview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.RatingAdapter
import com.example.popview.RatingItem

class RatingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        // Configuración del RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // Dos columnas

        // Lista de datos
        val seriesList = listOf(
            RatingItem("1.", R.drawable.sabrina, 4.0f),
            RatingItem("2.", R.drawable.strangerthingscuatro, 3.5f),
            RatingItem("3.", R.drawable.orange_is_the_new_black, 3.0f),
            RatingItem("4.", R.drawable.wednesdaymiercoles, 2.5f),
            RatingItem("5.", R.drawable.deadpoolylobezno, 4.0f),
            RatingItem("6.", R.drawable.delrevesdos, 3.5f)
        )

        // Configurar el adaptador
        recyclerView.adapter = RatingAdapter(seriesList)
    }
}
