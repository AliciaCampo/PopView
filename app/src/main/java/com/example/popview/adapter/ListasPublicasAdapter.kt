package com.example.popview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.data.ListaPublica

class ListasPublicasAdapter(
    private val listas: List<ListaPublica>,
    private val onItemClick: (ListaPublica) -> Unit
) : RecyclerView.Adapter<ListasPublicasAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitulo: TextView = itemView.findViewById(R.id.textViewTitulo)
        private val textViewDescripcion: TextView = itemView.findViewById(R.id.textViewDescripcion)

        fun bind(lista: ListaPublica, onItemClick: (ListaPublica) -> Unit) {
            textViewTitulo.text = lista.titol
            textViewDescripcion.text = lista.descripcion ?: "Sin descripción"

            itemView.setOnClickListener {
                onItemClick(lista)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_titulo_lista_publica, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listas[position], onItemClick)
    }

    override fun getItemCount(): Int = listas.size
}
