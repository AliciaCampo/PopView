package com.example.popview.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.activity.CrearListaActivity
import com.example.popview.data.ListItem
import com.example.popview.adapter.ListaFragmentAddapter
import com.example.popview.R
import kotlin.math.min

class AddTituloLista : DialogFragment() {

    private lateinit var itemList: MutableList<ListItem>
    private lateinit var adapter: ListaFragmentAddapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.activity_add_titulo_lista, null)

        // Configuración del RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewLists)
        itemList = mutableListOf(
            ListItem("Lista 1", false),
            ListItem("Lista 2", true),
            ListItem("Lista 3", false)
        )
        adapter = ListaFragmentAddapter(itemList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Campo de búsqueda
        val searchEditText = view.findViewById<EditText>(R.id.textBuscar)

        // Aseguramos que el TextWatcher esté configurado correctamente
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // Aquí puedes manejar acciones antes de que el texto cambie, si es necesario
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val query = charSequence.toString()
                filtrarListas(query)
            }

            override fun afterTextChanged(editable: Editable?) {
                // Aquí puedes manejar acciones después de que el texto haya cambiado, si es necesario
            }
        })

        // Botón de cancelar
        val bntCancelar = view.findViewById<Button>(R.id.btnCancel)
        bntCancelar.setOnClickListener {
            dismiss()
        }

        // LinearLayout para crear una nueva lista
        val linearLayout = view.findViewById<LinearLayout>(R.id.linearLayout)
        linearLayout.setOnClickListener {
            // Al hacer clic en el LinearLayout, redirigir a la actividad para crear una nueva lista
            val intent = Intent(requireContext(), CrearListaActivity::class.java)
            startActivity(intent)
        }

        builder.setView(view)
        return builder.create()
    }

    // Función para filtrar las listas por nombre usando similitud
    private fun filtrarListas(query: String) {
        val filteredItems = itemList.filter { item ->
            // Compara el nombre de cada lista con el texto de búsqueda usando similitud
            similarity(item.name, query) >= 60.0  // Umbral de 60% de similitud
        }
        adapter.updateList(filteredItems) // Actualiza la lista en el RecyclerView
    }

    // Función para calcular la distancia de Levenshtein
    private fun levenshteinDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
        for (i in s1.indices) dp[i][0] = i
        for (j in s2.indices) dp[0][j] = j

        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                dp[i][j] = if (s1[i - 1] == s2[j - 1]) {
                    dp[i - 1][j - 1]
                } else {
                    min(min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1
                }
            }
        }
        return dp[s1.length][s2.length]
    }

    // Función para calcular la similitud entre dos cadenas
    private fun similarity(s1: String, s2: String): Double {
        val maxLength = maxOf(s1.length, s2.length)
        return if (maxLength > 0) {
            (1.0 - levenshteinDistance(s1.lowercase(), s2.lowercase()).toDouble() / maxLength) * 100
        } else {
            0.0
        }
    }
}