package com.example.popview

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ValoracionTituloHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageContent: ImageView = itemView.findViewById(R.id.imageContent)
    val textTitle: TextView = itemView.findViewById(R.id.textTitle)
    val textDescription: TextView = itemView.findViewById(R.id.textDescription)
    val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
}
