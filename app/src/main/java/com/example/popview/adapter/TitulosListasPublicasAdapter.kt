package com.example.popview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.data.Titulo
class TitulosListasPublicasAdapter(private val titulos: List<Titulo>) :
    RecyclerView.Adapter<TitulosListasPublicasAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitulo: TextView = itemView.findViewById(R.id.textViewTitulo)
        private val textViewDescripcion: TextView = itemView.findViewById(R.id.textViewDescripcion)
        fun bind(titulo: Titulo) {
            textViewTitulo.text = titulo.nombre
            textViewDescripcion.text = titulo.description
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_titulo_lista_publica, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(titulos[position])
    }
    override fun getItemCount(): Int = titulos.size
}
