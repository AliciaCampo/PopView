package com.example.popview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.data.Titulo

class AdaptadorImagenes(
    private val listaTitulos: List<Titulo>,
    private val onClick: (Titulo) -> Unit
) : RecyclerView.Adapter<AdaptadorImagenes.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)

        fun bind(titulo: Titulo) {
            imageView.setImageResource(titulo.imagen)
            itemView.setOnClickListener { onClick(titulo) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaTitulos[position])
    }

    override fun getItemCount(): Int = listaTitulos.size
}
