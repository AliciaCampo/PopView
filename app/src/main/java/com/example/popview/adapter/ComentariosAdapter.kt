package com.example.popview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.data.Comentario

class ComentariosAdapter(
    private val comentarios: MutableList<Comentario>,
    private val onEditClick: (Comentario) -> Unit,
    private val onDeleteClick: (Comentario) -> Unit,
    private val onSendClick: (Comentario) -> Unit,
    private val currentUserId: Int
) : RecyclerView.Adapter<ComentariosAdapter.ComentarioViewHolder>() {

    class ComentarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val buttonEdit: ImageButton = itemView.findViewById(R.id.buttonEdit)
        val buttonDelete: ImageButton = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_valoracion_titulo, parent, false)
        return ComentarioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        val comentario = comentarios[position]
        Log.d("ComentariosAdapter", "Binding comentario: usuari_id=${comentario.usuari_id}, currentUserId=$currentUserId")
        holder.textDescription.text = comentario.comentaris
        holder.ratingBar.rating = comentario.rating

        if (comentario.usuari_id == currentUserId) {
            Log.d("ComentariosAdapter", "Mostrando botones para comentario de usuario actual")
            holder.buttonEdit.visibility = View.VISIBLE
            holder.buttonDelete.visibility = View.VISIBLE
            holder.buttonEdit.setOnClickListener {
                Log.d("ComentariosAdapter", "Editando comentario: usuari_id=${comentario.usuari_id}")
                onEditClick(comentario)
            }
            holder.buttonDelete.setOnClickListener {
                Log.d("ComentariosAdapter", "Eliminando comentario: usuari_id=${comentario.usuari_id}")
                onDeleteClick(comentario)
            }
        } else {
            Log.d("ComentariosAdapter", "Ocultando botones para comentario de otro usuario")
            holder.buttonEdit.visibility = View.GONE
            holder.buttonDelete.visibility = View.GONE
            holder.buttonEdit.setOnClickListener(null)
            holder.buttonDelete.setOnClickListener(null)
        }
    }

    override fun getItemCount() = comentarios.size
}