package com.example.popview.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popview.R
import com.example.popview.activity.CrearListaActivity
import com.example.popview.adapter.ListaFragmentAddapter
import com.example.popview.data.ListItem
import com.example.popview.service.PopViewAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        itemList = mutableListOf()  // Lista vacía que se llenará con Retrofit
        adapter = ListaFragmentAddapter(itemList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Llamada a la API para obtener las listas
        obtenerListas()

        // Campo de búsqueda
        val searchEditText = view.findViewById<EditText>(R.id.textBuscar)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val query = charSequence.toString()
                filtrarListas(query)
            }
            override fun afterTextChanged(editable: Editable?) {}
        })

        // Botón de cancelar
        val bntCancelar = view.findViewById<Button>(R.id.btnCancel)
        bntCancelar.setOnClickListener { dismiss() }

        // LinearLayout para crear una nueva lista
        val linearLayout = view.findViewById<LinearLayout>(R.id.linearLayout)
        linearLayout.setOnClickListener {
            val intent = Intent(requireContext(), CrearListaActivity::class.java)
            startActivity(intent)
        }

        builder.setView(view)
        return builder.create()
    }

    // Función para obtener las listas desde la API usando Retrofit
    private fun obtenerListas() {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    PopViewAPI().API().getAllLlistes()  // Llamada a la API
                }
                itemList = response.map { lista -> ListItem(lista.titulo, false) }.toMutableList()
                adapter.updateList(itemList)  // Actualiza el RecyclerView
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al obtenir les  llistes: ${e.message}")
                Toast.makeText(requireContext(), "Error al obtenir les llistes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para filtrar las listas por nombre usando similitud
    private fun filtrarListas(query: String) {
        val filteredItems = itemList.filter { item ->
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