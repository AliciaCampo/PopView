package com.example.popview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popview.R
import com.example.popview.data.Item

class RatingAdapter(private val itemList: List<Item>) :
    RecyclerView.Adapter<RatingAdapter.RatingViewHolder>() {

    class RatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numRating: TextView = itemView.findViewById(R.id.numrating)
        val imageView: ImageView = itemView.findViewById(R.id.imageSabrina)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar01)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plantilla_rating, parent, false)
        return RatingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
        val item = itemList[position]
        holder.numRating.text = item.title

        // Usar Glide para cargar la imagen desde la URL
        Glide.with(holder.itemView.context)
            .load("http://44.205.116.170/PopView_fotos/${item.imageUrl}")
            .into(holder.imageView)

        holder.ratingBar.rating = item.rating

        holder.imageView.setOnClickListener {
            // Manejar el evento de clic si es necesario
        }
    }

    override fun getItemCount(): Int = itemList.size
}
