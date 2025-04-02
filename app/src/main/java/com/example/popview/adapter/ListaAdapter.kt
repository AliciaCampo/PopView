package com.example.popview.adapter

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.PopViewApp
import com.example.popview.R
import com.example.popview.data.Lista
import com.example.popview.service.PopViewAPI
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListasAdapter(
    private val listas: MutableList<Lista>,
    private val onItemClicked: (Lista) -> Unit
) : RecyclerView.Adapter<ListasAdapter.ListaViewHolder>() {
    private val popViewService = PopViewAPI().API()
    class ListaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.textTitulo)
        val estado: TextView = view.findViewById(R.id.textEstado) // TextView para mostrar el estado
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false)
        return ListaViewHolder(view)
    }
    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        val lista = listas[position]
        holder.titulo.text = lista.titulo
        val estadoText = if (lista.esPrivada) "Privada" else "Pública"
        holder.estado.text = estadoText
        holder.itemView.setOnClickListener {
            onItemClicked(lista)
        }
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(it.context)
                .setTitle("Confirma l'eliminació")
                .setMessage("Estàs segur que vols esborrar aquesta llista?")
                .setPositiveButton("Sí") { dialog, _ ->
                    // Eliminar de la llista local i notificar a l'adapter
                    listas.removeAt(position)
                    notifyItemRemoved(position)
                    registrarListaEliminada()
                    // Crida a l'API per eliminar la llista
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            popViewService.deleteLista(lista.id)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel·lar") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
            true
        }
    }
    private fun registrarListaEliminada() {
        val db = FirebaseFirestore.getInstance()
        val deviceRef = db.collection("Devices").document(PopViewApp.idDispositiu)

        deviceRef.update("listasEliminadas", FieldValue.increment(1))
            .addOnSuccessListener {
                Log.d("PopViewApp", "Lista registrado correctamente en Devices")
            }
            .addOnFailureListener { e ->
                Log.e("PopViewApp", "Error al registrar lista en Devices", e)
            }
    }
    override fun getItemCount(): Int = listas.size
}