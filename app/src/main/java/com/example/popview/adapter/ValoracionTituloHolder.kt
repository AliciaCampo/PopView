package com.example.popview.adapter

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R

class ValoracionTituloHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageContent: ImageView = itemView.findViewById(R.id.imageContent)
    val textTitle: TextView = itemView.findViewById(R.id.textTitle)
    val textDescription: TextView = itemView.findViewById(R.id.textDescription)
    val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
}
