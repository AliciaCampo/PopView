package  com.example.popview
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.Item
import com.example.popview.MainActivity
import com.example.popview.R
import com.example.popview.ValoracionTituloAdapter

class ValoracionTituloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_valoracion_titulo)

        // Configurar botón de retroceso
        val imageButtonEnrere: ImageButton = findViewById(R.id.imageButtonEnrere)
        imageButtonEnrere.setOnClickListener {
            // Volver a la pantalla principal
            val intentEnrere = Intent(this, MainActivity::class.java)
            startActivity(intentEnrere)
            finish()
        }

        // Configurar RecyclerView para comentarios dinámicos
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Crear lista de comentarios o ítems
        val items = listOf(
            Item("Comentario 1", "Este es un comentario 1.", R.drawable.deadpoolylobezno, 4.0f),
            Item("Comentario 2", "Este es un comentario 2.", R.drawable.deadpoolylobezno, 3.5f),
            Item("Comentario 3", "Este es un comentario 3.", R.drawable.deadpoolylobezno, 5.0f)
        )

        // Configurar adaptador
        val adapter = ValoracionTituloAdapter(items)
        recyclerView.adapter = adapter
    }
}
