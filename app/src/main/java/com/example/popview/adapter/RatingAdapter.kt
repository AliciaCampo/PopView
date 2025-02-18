package com.example.popview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.data.RatingItem

class RatingAdapter(private val itemList: List<RatingItem>) :
    RecyclerView.Adapter<RatingAdapter.RatingViewHolder>() {

    // ViewHolder para mantener las vistas
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
        holder.imageView.setImageResource(item.imageResId)
        holder.ratingBar.rating = item.rating

        // Configuración opcional para clics en el ImageButton
        holder.imageView.setOnClickListener {
            // Puedes manejar el evento de clic aquí, si es necesario
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
