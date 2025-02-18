package com.example.popview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.data.ListItem
import com.example.popview.R

class ListaFragmentAddapter(private val items: List<ListItem>) : RecyclerView.Adapter<ListaFragmentAddapter.ViewHolder>() {

    // ViewHolder para cada item
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkImage: ImageView = itemView.findViewById(R.id.check)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Obtén el item de la lista en esa posición
        val currentItem = items[position]

        // Establece el texto en el TextView
        holder.textView.text = currentItem.name

        // Cambia la imagen del check dependiendo del estado
        if (currentItem.isChecked) {
            holder.checkImage.setImageResource(R.drawable.check_done)
        } else {
            holder.checkImage.setImageResource(R.drawable.check_undone)
        }

        // Establece el comportamiento del clic en el check
        holder.checkImage.setOnClickListener {
            // Cambia el estado de isChecked
            currentItem.isChecked = !currentItem.isChecked

            // Notifica que solo ese ítem ha cambiado
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = items.size
}