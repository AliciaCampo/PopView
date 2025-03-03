package com.example.popview.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popview.R
import com.example.popview.activity.ValoracionTituloActivity
import com.example.popview.data.Item
import com.example.popview.data.Titulo

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
        Glide.with(holder.itemView.context)
            .load("http://44.205.116.170/${item.imageUrl}")
            .into(holder.imageView)
        holder.ratingBar.rating = item.rating

        holder.imageView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ValoracionTituloActivity::class.java)

            // Ahora se pasan todos los parámetros requeridos al objeto Titulo
            val titulo = Titulo(
                nombre = item.title,
                description = item.description,  // Cambiar cuando tengas los datos reales
                imagen = item.imageUrl,
                rating = item.rating,
                platforms = item.platforms,
                comments = listOf(),  // Aquí debes pasar los comentarios si tienes, de lo contrario, pasar una lista vacía
                edadRecomendada = item.edadRecomendad, // Cambiar a la edad recomendada si la tienes
                genero = item.genero // Cambiar por el género real si lo tienes
            )

            intent.putExtra("titulo", titulo)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = itemList.size
}
