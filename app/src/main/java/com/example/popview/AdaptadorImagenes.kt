package com.example.popview
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
class AdaptadorImagenes(
    private val listaImagenes: List<ImageItem>,
    private val onClickImagen: (ImageItem) -> Unit
) : RecyclerView.Adapter<AdaptadorImagenes.VistaHolderImagenes>() {

    inner class VistaHolderImagenes(vista: View) : RecyclerView.ViewHolder(vista) {
        val botonImagen: ImageView = vista.findViewById(R.id.botonImagen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaHolderImagenes {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fila_imagenes, parent, false)
        return VistaHolderImagenes(vista)
    }

    override fun onBindViewHolder(holder: VistaHolderImagenes, position: Int) {
        val itemImagen = listaImagenes[position]
        // Usar Glide para cargar las imágenes
        Glide.with(holder.botonImagen.context)
            .load(itemImagen.imageUrl)
            .into(holder.botonImagen)
        // Manejo de clics
        holder.botonImagen.setOnClickListener { onClickImagen(itemImagen) }
    }
    override fun getItemCount(): Int = listaImagenes.size
}
