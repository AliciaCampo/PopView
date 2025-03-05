package com.example.popview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.data.ListItem
import com.example.popview.R

class ListaFragmentAddapter(
    private var items: MutableList<ListItem>,
    private val onItemClick: (ListItem) -> Unit // Callback para manejar clics en los ítems
) : RecyclerView.Adapter<ListaFragmentAddapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkImage: ImageView = itemView.findViewById(R.id.check)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]

        holder.textView.text = currentItem.name
        holder.checkImage.setImageResource(if (currentItem.isChecked) R.drawable.check_done else R.drawable.check_undone)

        // Cuando se hace clic en el ítem, llamamos al callback para agregar la película a la lista
        holder.itemView.setOnClickListener {
            onItemClick(currentItem)  // Llamar al callback para agregar el título a la lista
        }
    }

    override fun getItemCount() = items.size

    fun updateList(newItems: List<ListItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
    fun cambiarImagen(listaId: Int) {
        val index = items.indexOfFirst { it.id == listaId }
        if (index != -1) {
            // Cambiar el estado de isChecked a true (marcado)
            items[index].isChecked = true
            notifyItemChanged(index)  // Notificar al RecyclerView para actualizar la UI
        }
    }

}
