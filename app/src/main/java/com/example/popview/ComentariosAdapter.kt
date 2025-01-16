package com.example.popview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ComentariosAdapter(private val comentarios: List<String>) :
    RecyclerView.Adapter<ComentariosAdapter.ComentarioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_comentario,
            parent,
            false
        )
        return ComentarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        holder.bind(comentarios[position])
    }

    override fun getItemCount() = comentarios.size

    class ComentarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textComentario: TextView = view.findViewById(R.id.textComentario)

        fun bind(comentario: String) {
            textComentario.text = comentario
        }
    }
}
