package com.example.popview

import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Verificar si el contenedor del fragmento está vacío
        if (savedInstanceState == null) {
            // Cargar el fragmento usando el FragmentManager
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, barramenu()) // Añadimos el fragmento
                .commit()
        }


    }
}
