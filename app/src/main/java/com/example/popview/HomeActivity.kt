package com.example.popview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inicializar el RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewImage)

        // Crear una lista de objetos ImageItem
        val imageList = listOf(
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
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Configurar el Adapter
        recyclerView.adapter = ImageAdapter(imageList)
    }
}
