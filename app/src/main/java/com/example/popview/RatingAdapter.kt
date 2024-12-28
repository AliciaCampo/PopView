package com.example.popview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RatingAdapter(private val items: List<RatingItem>) : RecyclerView.Adapter<RatingViewHolder>() {

    // Infla el disseny de l'element i crea un ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RatingViewHolder(inflater, parent)
    }

    // Vincula les dades al ViewHolder
    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    // Retorna el número d'elements
    override fun getItemCount(): Int = items.size
}