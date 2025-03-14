package com.example.popview.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popview.data.ImageItem
import com.example.popview.R
import com.example.popview.activity.ValoracionTituloActivity

class ImageAdapter(private val imageList: List<ImageItem>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.plantimg_imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plantilla_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = imageList[position]
        Glide.with(holder.itemView.context)
            .load("http://44.205.116.170/${currentItem.imageUrl}")
            .into(holder.imageView)
        holder.imageView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ValoracionTituloActivity::class.java)
            intent.putExtra("titulo", currentItem.titulo)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int = imageList.size
}

