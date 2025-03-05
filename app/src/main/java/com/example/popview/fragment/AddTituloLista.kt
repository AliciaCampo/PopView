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

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewLists)
        itemList = mutableListOf()
        adapter = ListaFragmentAddapter(itemList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        obtenerListas()

        val searchEditText = view.findViewById<EditText>(R.id.textBuscar)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                filtrarListas(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val bntCancelar = view.findViewById<Button>(R.id.btnCancel)
        bntCancelar.setOnClickListener { dismiss() }

        val linearLayout = view.findViewById<LinearLayout>(R.id.linearLayout)
        linearLayout.setOnClickListener {
            val intent = Intent(requireContext(), CrearListaActivity::class.java)
            startActivity(intent)
        }

        builder.setView(view)
        return builder.create()
    }

    private fun obtenerListas() {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    PopViewAPI().API().getAllLlistes()
                }
                itemList = response.map { lista -> ListItem(lista.titulo, false) }.toMutableList()
                adapter.updateList(itemList)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al obtener las listas: ${e.message}")
                Toast.makeText(requireContext(), "Error al obtener las listas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun filtrarListas(query: String) {
        if (query.isEmpty()) {
            adapter.updateList(itemList)  // Si el usuario borra el texto, mostramos todos los elementos
            return
        }

        val filteredItems = itemList.filter { item ->
            val similarityScore = similarity(item.name, query)
            Log.d("SEARCH", "Comparando '${item.name}' con '$query' → Similitud: $similarityScore%")
            similarityScore >= 60.0
        }

        adapter.updateList(filteredItems)
    }

    private fun levenshteinDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }

        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j

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

    private fun similarity(s1: String, s2: String): Double {
        val s1Lower = s1.lowercase().trim()
        val s2Lower = s2.lowercase().trim()
        val maxLength = maxOf(s1Lower.length, s2Lower.length)

        if (maxLength == 0) return 100.0  // Si ambas cadenas están vacías, se consideran 100% similares.

        val distance = levenshteinDistance(s1Lower, s2Lower)
        return (1.0 - distance.toDouble() / maxLength) * 100
    }
}
