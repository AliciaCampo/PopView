package com.example.popview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.PopViewApp
import com.example.popview.R
import com.example.popview.data.Titulo
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class PeliculasAdapter(
    private val peliculas: List<Titulo>,
    private val onDeleteClick: (Titulo) -> Unit
) : RecyclerView.Adapter<PeliculasAdapter.PeliculaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_titulo, parent, false)
        return PeliculaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val pelicula = peliculas[position]
        holder.textViewTitulo.text = pelicula.nombre  // Asegúrate de que la clase Titulo tenga esta propiedad
        holder.imageViewIcono.setImageResource(R.drawable.x)

        holder.imageViewIcono.setOnClickListener {
            onDeleteClick(pelicula)
            registrarTituloEliminado()
        }
    }

    override fun getItemCount() = peliculas.size

    class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitulo: TextView = itemView.findViewById(R.id.textViewTitulo)
        val imageViewIcono: ImageView = itemView.findViewById(R.id.imagenX)
    }
    private fun registrarTituloEliminado() {
        val db = FirebaseFirestore.getInstance()
        val deviceRef = db.collection("Devices").document(PopViewApp.idDispositiu)
        deviceRef.update("titulosEliminados", FieldValue.increment(1))
            .addOnSuccessListener {
                Log.d("PopViewApp", "Titulo eliminado registrado en Devices")
            }
            .addOnFailureListener { e ->
                Log.e("PopViewApp", "Error al actualizar listas editadas en Devices", e)
            }
    }
}
