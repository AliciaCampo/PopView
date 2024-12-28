package com.example.popview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class ListasAdapter(
    private val listas: MutableList<Lista>,
    private val onItemClicked: (Lista) -> Unit
) : RecyclerView.Adapter<ListasAdapter.ListaViewHolder>() {

    class ListaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.textTitulo)
        val estado: TextView = view.findViewById(R.id.textEstado) // TextView para mostrar el estado
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false)
        return ListaViewHolder(view)
    }
    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        val lista = listas[position]
        holder.titulo.text = lista.titulo
        // Mostrar si es pública o privada
        val estadoText = if (lista.esPrivada) "Privada" else "Pública"
        holder.estado.text = estadoText
        holder.itemView.setOnClickListener {
            onItemClicked(lista)
        }
        holder.itemView.setOnLongClickListener {
            listas.removeAt(position) // Eliminar la lista
            notifyItemRemoved(position) // Notificar al adaptador
            true
        }
    }
    override fun getItemCount(): Int = listas.size
}