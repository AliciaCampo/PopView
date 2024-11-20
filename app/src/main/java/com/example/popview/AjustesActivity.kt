package com.example.popview

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AjustesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes)

        // Configurar el Spinner de idioma
        val spinnerIdioma = findViewById<Spinner>(R.id.spinnerIdioma)

        // Usar datos de un recurso XML
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.idiomas_array,  // Asegúrate de tener este recurso en strings.xml
            android.R.layout.simple_spinner_item
        )

        // Configura el diseño para los ítems desplegables del Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asigna el adaptador al Spinner
        spinnerIdioma.adapter = adapter

        // Manejo de selección en el Spinner
        spinnerIdioma.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val idiomaSeleccionado = parent.getItemAtPosition(position).toString()
                Toast.makeText(applicationContext, "Seleccionaste: $idiomaSeleccionado", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Maneja el caso donde no se seleccionó nada (opcional)
            }
        }

        // Manejo de los márgenes de los bordes de la pantalla (Edge to Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
