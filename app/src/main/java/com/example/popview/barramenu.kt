package com.example.popview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class barramenu : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_barramenu, container, false)

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        // Desmarcar cualquier ítem seleccionado por defecto
        bottomNavigationView.selectedItemId = -1  // Esto desmarca el ítem seleccionado por defecto.

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(activity, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_search -> {
                    val intent = Intent(activity, BuscarActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_rating -> {
                    val intent = Intent(activity, RatingActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_user -> {
                    val intent = Intent(activity, UsuarioActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_settings -> {
                    val intent = Intent(activity, AjustesActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        return view
    }
}

