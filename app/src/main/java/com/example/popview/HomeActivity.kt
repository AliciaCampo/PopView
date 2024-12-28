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

        // Crear una lista de objetos ImageItem (que representan las imágenes)
        val imageList = listOf(
            ImageItem(R.drawable.deadpoolylobezno),
            ImageItem(R.drawable.delrevesdos),
            ImageItem(R.drawable.beetlejuice2)
        )

        // Configurar el LayoutManager para el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Establecer el Adapter para el RecyclerView
        recyclerView.adapter = Home_buscarAdapter(imageList)
    }
}
