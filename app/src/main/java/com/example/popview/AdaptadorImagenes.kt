package com.example.popview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
class AdaptadorImagenes(
    private val listaImagenes: List<ImageItem>,
    private val onClickImagen: (ImageItem) -> Unit
) : RecyclerView.Adapter<AdaptadorImagenes.VistaHolderImagenes>() {
    inner class VistaHolderImagenes(vista: View) : RecyclerView.ViewHolder(vista) {
        val botonPrimeraImagen: ImageButton = vista.findViewById(R.id.botonPrimeraImagen)
        val botonSegundaImagen: ImageButton = vista.findViewById(R.id.botonSegundaImagen)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaHolderImagenes {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fila_imagenes, parent, false)
        return VistaHolderImagenes(vista)
    }
    override fun onBindViewHolder(holder: VistaHolderImagenes, position: Int) {
        // Obtener las dos imágenes correspondientes a la fila actual
        val imagenPrimera = listaImagenes[position * 2]
        val imagenSegunda = listaImagenes[position * 2 + 1]
        // Usar Glide para cargar las imágenes desde URLs o recursos
        Glide.with(holder.botonPrimeraImagen.context)
            .load(imagenPrimera.imageUrl)
            .into(holder.botonPrimeraImagen)
        Glide.with(holder.botonSegundaImagen.context)
            .load(imagenSegunda.imageUrl)
            .into(holder.botonSegundaImagen)
        // Manejo de clics
        holder.botonPrimeraImagen.setOnClickListener { onClickImagen(imagenPrimera) }
        holder.botonSegundaImagen.setOnClickListener { onClickImagen(imagenSegunda) }
    }
    override fun getItemCount(): Int {
        // Calculamos la cantidad de filas (dos imágenes por fila)
        return listaImagenes.size / 2
    }
}