package com.example.popview


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class BuscarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        AppContext.context = this

        //logica del recycler view
        val recyclerViewContent = findViewById<RecyclerView>(R.id.recyclerViewContent)
        //configuración del recycler view
        recyclerViewContent.layoutManager = GridLayoutManager(this, 2) // Dos columnas
        //lista de las imagenes
        val listaTitulos = listOf(
            Titulo(
                imagen = R.drawable.sabrina,
                nombre = "Sabrina",
                description = AppContext.context.getString(R.string.description_sabrina),
                platforms = listOf("Netflix", "Amazon Prime"),
                rating = 3.5f,//se cojera de la base de datos del user
                comments = listOf() // Por ahora vacío, mantenemos como placeholder
            ),
            Titulo(
                imagen = R.drawable.strangerthingscuatro,
                nombre = "Stranger Things",
                description = AppContext.context.getString(R.string.description_stranger),
                platforms = listOf("Netflix"),
                rating = 4f,
                comments = listOf()
            ),
            Titulo(
                imagen = R.drawable.orange_is_the_new_black,
                nombre = "Orange is the new black",
                description = AppContext.context.getString(R.string.description_orangeisthenewblack),
                platforms = listOf("Netflix"),
                rating = 4f,
                comments = listOf()
            ),
            Titulo(
                imagen = R.drawable.wednesdaymiercoles,
                nombre = "Miercoles",
                description = AppContext.context.getString(R.string.description_miercoles),
                platforms = listOf("Netflix"),
                rating = 4f,
                comments = listOf()
            ),Titulo(
                imagen = R.drawable.deadpoolylobezno,
                nombre = "Deadpool y Lobezno",
                description = AppContext.context.getString(R.string.description_deadpoolylobezno),
                platforms = listOf("Netflix"),
                rating = 4f,
                comments = listOf()
            ),
            Titulo(
                imagen = R.drawable.delrevesdos,
                nombre = "Del Reves 2",
                description = AppContext.context.getString(R.string.description_delrevesdos),
                platforms = listOf("Disney"),
                rating = 4f,
                comments = listOf()
            ),
            Titulo(
                imagen = R.drawable.respira,
                nombre = "Respira",
                description = AppContext.context.getString(R.string.description_respira),
                platforms = listOf("Netflix"),
                rating = 4f,
                comments = listOf()
            ),
            Titulo(
                imagen = R.drawable.beetlejuice2,
                nombre = "Beetlejuice 2",
                description = AppContext.context.getString(R.string.description_beetlejuicedos),
                platforms = listOf("Netflix"),
                rating = 4f,
                comments = listOf()
            ),
            // Agrega los otros títulos de la misma manera
        )

        // Configurar el adaptador
        val adaptador = AdaptadorImagenes(listaTitulos) { titulo ->
            val intent = Intent(this@BuscarActivity, ValoracionTituloActivity::class.java)
            intent.putExtra("titulo", titulo)
            startActivity(intent)
        }
        recyclerViewContent.adapter = adaptador

        val imageFiltro = findViewById<ImageView>(R.id.imageFiltro)
        // Lista de filtros y su estado seleccionado
        val filtros = arrayOf("+12", "+16", "+18", "Sèrie", "Pel·lícula", "Acció", "Fantasía", "Superherois", "Comèdia", "Director")
        val seleccionados = BooleanArray(filtros.size) { false } // Estado de selección inicial: ninguno seleccionado
        imageFiltro.setOnClickListener {
            // Crear el diálogo con opciones múltiples seleccionables
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Selecciona un o més filtres")
            builder.setMultiChoiceItems(filtros, seleccionados) { _, which, isChecked ->
                // Actualizar el estado de selección
                seleccionados[which] = isChecked
            }
            builder.setPositiveButton("Acceptar") { dialog, _ ->
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel·lar") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
}