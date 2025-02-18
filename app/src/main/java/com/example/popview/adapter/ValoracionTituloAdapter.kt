package com.example.popview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.data.Item

class ValoracionTituloAdapter(
    private val items: List<Item>
) : RecyclerView.Adapter<ValoracionTituloHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValoracionTituloHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_valoracion_titulo, parent, false)
        return ValoracionTituloHolder(view)
    }

    override fun onBindViewHolder(holder: ValoracionTituloHolder, position: Int) {
        val item = items[position]
        holder.textTitle.text = item.title
        holder.textDescription.text = item.description
        holder.imageContent.setImageResource(item.imageResId)
        holder.ratingBar.rating = item.rating
    }

    override fun getItemCount(): Int = items.size
}
