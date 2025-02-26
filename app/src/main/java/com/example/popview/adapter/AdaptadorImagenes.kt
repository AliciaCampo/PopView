package com.example.popview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popview.R
import com.example.popview.data.Titulo

class AdaptadorImagenes(
    private val titulos: List<Titulo>,
    private val onItemClick: (Titulo) -> Unit
) : RecyclerView.Adapter<AdaptadorImagenes.TituloViewHolder>() {

    class TituloViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TituloViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plantilla_image, parent, false)
        return TituloViewHolder(view)
    }

    override fun onBindViewHolder(holder: TituloViewHolder, position: Int) {
        val titulo = titulos[position]
        Glide.with(holder.itemView.context)
            .load("http://44.205.116.170/${titulo.imagen}")
            .into(holder.imageView)
        holder.textView.text = titulo.nombre
        holder.itemView.setOnClickListener { onItemClick(titulo) }
    }

    override fun getItemCount(): Int = titulos.size
}
