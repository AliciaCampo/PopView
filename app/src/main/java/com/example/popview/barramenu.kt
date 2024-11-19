package com.example.popview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class barramenu : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_barramenu, container, false)
        // Referenciamos los elementos del layout por su ID
        val home = view.findViewById<ImageView>(R.id.home)
        val buscar = view.findViewById<ImageView>(R.id.buscar)
        val estrella = view.findViewById<ImageView>(R.id.estrella)
        val cuenta = view.findViewById<ImageView>(R.id.cuenta)
        val ajustes = view.findViewById<ImageView>(R.id.ajustes)

        // Configurar el evento de clic para cada ImageView
        // Cuando se hace clic en el icono de 'home'
        home.setOnClickListener {
            // Navegamos a HomeActivity
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
        }
        // Cuando se hace clic en el icono de 'buscar'
        buscar.setOnClickListener {
            // Navegamos a BuscarActivity
            val intent = Intent(activity, BuscarActivity::class.java)
            startActivity(intent)
        }
        // Cuando se hace clic en el icono de 'estrella'
        estrella.setOnClickListener {
            // Navegamos a RatingActivity
            val intent = Intent(activity, RatingActivity::class.java)
            startActivity(intent)
        }
        // Cuando se hace clic en el icono de 'cuenta'
        cuenta.setOnClickListener {
            // Navegamos a UsuarioActivity
            val intent = Intent(activity, UsuarioActivity::class.java)
            startActivity(intent)
        }
        // Cuando se hace clic en el icono de 'ajustes'
        ajustes.setOnClickListener {
            // Navegamos a AjustesActivity
            val intent = Intent(activity, AjustesActivity::class.java)
            startActivity(intent)
        }
        return view // Retornamos la vista inflada
    }
}
