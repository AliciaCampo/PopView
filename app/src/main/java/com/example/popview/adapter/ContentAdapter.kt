package com.example.popview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.data.Usuario
import com.example.popview.data.Lista

class ContentAdapter : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {

    private var datos: List<Any> = emptyList()

    // Metodo para actualizar los datos del adaptador
    fun actualizarDatos(nuevosDatos: List<Any>) {
        datos = nuevosDatos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fila_buscarlistas, parent, false)
        return ContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val item = datos[position]
        // Aquí debes manejar la vinculación de datos según el tipo de item (Usuario o Lista)
        if (item is Usuario) {
            holder.bindUsuario(item)
        } else if (item is Lista) {
            holder.bindLista(item)
        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
        private val textViewDescripcion: TextView = itemView.findViewById(R.id.textViewDescripcion)

        fun bindUsuario(usuario: Usuario) {
            textViewNombre.text = usuario.nombre
            textViewDescripcion.text = usuario.correo
        }

        fun bindLista(lista: Lista) {
            textViewNombre.text = lista.titulo
            textViewDescripcion.text = lista.descripcion
        }
    }
}
