import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R

class PeliculasAdapter(private val peliculasList: MutableList<String>) : RecyclerView.Adapter<PeliculasAdapter.PeliculaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false)
        return PeliculaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val pelicula = peliculasList[position]
        holder.bind(pelicula)

        // Configura el botón de eliminar
        holder.itemView.findViewById<ImageButton>(R.id.btnEliminar).setOnClickListener {
            // Eliminar la película de la lista
            removeItem(position)
        }
    }

    override fun getItemCount(): Int = peliculasList.size

    private fun removeItem(position: Int) {
        // Eliminar el ítem de la lista
        peliculasList.removeAt(position)
        // Notificar al adaptador para actualizar el RecyclerView
        notifyItemRemoved(position)
    }

    // ViewHolder para manejar los elementos del RecyclerView
    inner class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pelicula: String) {
            itemView.findViewById<TextView>(R.id.textTitulo).text = pelicula
        }
    }
}