package com.example.popview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R

class PeliculasAdapter(private val peliculas: List<String>) : RecyclerView.Adapter<PeliculasAdapter.PeliculaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        // Inflar el layout personalizado
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_titulo, parent, false)
        return PeliculaViewHolder(view)
    }
    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val peliculaTitulo = peliculas[position]
        // Configurar el texto del título
        holder.textViewTitulo.text = peliculaTitulo
        // Configurar la imagen (si necesitas que cambie dependiendo del ítem, puedes añadir lógica aquí)
        holder.imageViewIcono.setImageResource(R.drawable.x) // Usa la imagen "x"
    }
    override fun getItemCount() = peliculas.size
    // ViewHolder para el RecyclerView
    class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitulo: TextView = itemView.findViewById(R.id.textViewTitulo)
        val imageViewIcono: ImageView = itemView.findViewById(R.id.imagenX)
    }
}
