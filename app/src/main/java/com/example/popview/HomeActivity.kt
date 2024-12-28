package com.example.popview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inicializar el primer RecyclerView (Recomendaciones)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewRecomancions)

        // Crear una lista de objetos ImageItem para el primer RecyclerView
        val recyclerViewRecmancions = listOf(
            ImageItem(R.drawable.deadpoolylobezno),
            ImageItem(R.drawable.delrevesdos),
            ImageItem(R.drawable.beetlejuice2),
            ImageItem(R.drawable.wednesdaymiercoles),
            ImageItem(R.drawable.juegocalamar),
            ImageItem(R.drawable.casapapel),
            ImageItem(R.drawable.jokerdos),
            ImageItem(R.drawable.venomtres),
            ImageItem(R.drawable.robotsalvaje)
        )

        // Configurar el LayoutManager para desplazamiento horizontal
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Configurar el Adapter para el primer RecyclerView
        recyclerView.adapter = ImageAdapter(recyclerViewRecmancions)

        // Añadir espaciado entre los elementos
        recyclerView.addItemDecoration(ItemSpacingDecoration(16)) // Ajusta el valor si es necesario

        // Inicializar el segundo RecyclerView (Películas Populares)
        val recyclerViewBottom: RecyclerView = findViewById(R.id.recyclerViewImagePelisPop)

        // Crear una lista de objetos ImageItem para el segundo RecyclerView
        val recyclerViewPelisPop = listOf(
            ImageItem(R.drawable.deadpoolylobezno),
            ImageItem(R.drawable.delrevesdos),
            ImageItem(R.drawable.beetlejuice2),
            ImageItem(R.drawable.wednesdaymiercoles),
            ImageItem(R.drawable.juegocalamar),
            ImageItem(R.drawable.casapapel),
            ImageItem(R.drawable.jokerdos),
            ImageItem(R.drawable.venomtres),
            ImageItem(R.drawable.robotsalvaje)
        )

        // Configurar el LayoutManager para desplazamiento horizontal en el segundo RecyclerView
        recyclerViewBottom.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Configurar espaciado en el segundo RecyclerView
        recyclerViewBottom.addItemDecoration(ItemSpacingDecoration(8)) // Ajusta el valor si es necesario

        // Configurar el Adapter para el segundo RecyclerView
        recyclerViewBottom.adapter = ImageAdapter(recyclerViewPelisPop)
    }
}
