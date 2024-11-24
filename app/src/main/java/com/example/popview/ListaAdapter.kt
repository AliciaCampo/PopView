package com.example.popview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListasAdapter(
    private val listas: List<Lista>,
    private val onItemClicked: (Lista) -> Unit
) : RecyclerView.Adapter<ListasAdapter.ListaViewHolder>() {

    class ListaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.textTitulo)
        val switchPrivada: Switch = view.findViewById(R.id.switchPrivada)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lista, parent, false)
        return ListaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        val lista = listas[position]
        holder.titulo.text = lista.titulo
        holder.switchPrivada.isChecked = lista.esPrivada

        holder.itemView.setOnClickListener {
            onItemClicked(lista)
        }
    }

    override fun getItemCount(): Int = listas.size
}
