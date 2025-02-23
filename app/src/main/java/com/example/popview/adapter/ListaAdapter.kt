package com.example.popview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.data.Lista
import com.example.popview.service.PopViewAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListasAdapter(
    private val listas: MutableList<Lista>,
    private val onItemClicked: (Lista) -> Unit
) : RecyclerView.Adapter<ListasAdapter.ListaViewHolder>() {

    private val popViewService = PopViewAPI().API()

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
            // Eliminar la lista localmente
            listas.removeAt(position)
            notifyItemRemoved(position)
            // Eliminar la lista del servidor
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    popViewService.deleteLista(lista.id)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            true
        }
    }
    override fun getItemCount(): Int = listas.size
}