package com.example.popview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Home_buscarAdapter(private val imageList: List<ImageItem>) : RecyclerView.Adapter<Home_buscarAdapter.ImageViewHolder>() {

    // ViewHolder que contiene el ImageView
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    // Inflar el layout de cada ítem (usando item_image.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plantilla_image, parent, false)
        return ImageViewHolder(view)
    }

    // Vincula los datos (URL de la imagen) con el ImageView
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = imageList[position]
        Glide.with(holder.itemView.context)
            .load(currentItem.imageUrl) // Cargar la imagen desde la URL
            .into(holder.imageView)     // Mostrarla en el ImageView
    }

    override fun getItemCount(): Int = imageList.size
}
