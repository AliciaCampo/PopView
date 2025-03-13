package com.example.popview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R

class TitulosListasPublicasAdapter(
    private val titulos: List<String>
) : RecyclerView.Adapter<TitulosListasPublicasAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitulo: TextView = itemView.findViewById(R.id.textViewTitulo)

        fun bind(titulo: String) {
            textViewTitulo.text = titulo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_titulo, parent, false) // Asegúrate de usar el layout correcto
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(titulos[position])
    }
    override fun getItemCount(): Int = titulos.size
}