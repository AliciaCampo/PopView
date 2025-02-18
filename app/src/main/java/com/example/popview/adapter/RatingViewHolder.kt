package com.example.popview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.data.RatingItem

class RatingViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_rating, parent, false)) {

    private val titleTextView: TextView = itemView.findViewById(R.id.textTitle)
    private val imageView: ImageView = itemView.findViewById(R.id.imageItem)
    private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

    // Mètode per vincular les dades a les vistas
    fun bind(item: RatingItem) {
        titleTextView.text = item.title
        imageView.setImageResource(item.imageResId)
        ratingBar.rating = item.rating
    }
}
