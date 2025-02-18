package com.example.popview.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.activity.CrearListaActivity
import com.example.popview.data.ListItem
import com.example.popview.adapter.ListaFragmentAddapter
import com.example.popview.R

class AddTituloLista : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.activity_add_titulo_lista, null)
        //configuración el recycler view
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewLists)

        val itemList = mutableListOf(
            ListItem("Lista 1", false),
            ListItem("Lista 2", true),
            ListItem("Lista 3", false)
        )
        val adapter = ListaFragmentAddapter(itemList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        val bntCancelar = view.findViewById<Button>(R.id.btnCancel)
        bntCancelar.setOnClickListener {
            dismiss()
        }
        val linearLayout = view.findViewById<LinearLayout>(R.id.linearLayout)
        linearLayout.setOnClickListener {
            // Al hacer clic en cualquier parte del LinearLayout
            val intent = Intent(requireContext(), CrearListaActivity::class.java)
            startActivity(intent)
        }

        builder.setView(view)
        return builder.create()
    }
     fun getLists(): List<String> {
        return listOf("Lista 1", "Lista 2", "Lista 3")
    }
}