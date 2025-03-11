package com.example.popview.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.adapter.TitulosListasPublicasAdapter
import com.example.popview.data.Lista

class DetalleListaPublicaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_lista_publica)

        val lista = intent.getSerializableExtra("lista") as? Lista
        lista?.let {
            findViewById<TextView>(R.id.tituloLista).text = it.titulo

            val titulosRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewTitulos)
            titulosRecyclerView.layoutManager = LinearLayoutManager(this)
            titulosRecyclerView.adapter = TitulosListasPublicasAdapter(it.titulos)
        }
    }
}
