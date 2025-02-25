package com.example.popview.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.popview.data.Lista
import com.example.popview.R
import com.example.popview.service.PopViewAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrearListaActivity : AppCompatActivity() {
    private val popViewService = PopViewAPI().API()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_lista)
        val editTextTitulo = findViewById<EditText>(R.id.editTextTitulo)
        val switchPrivada = findViewById<Switch>(R.id.switchPrivada)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val imageButtonEnrere: ImageButton = findViewById(R.id.imageButtonEnrere)
        imageButtonEnrere.setOnClickListener {
            finish()
        }
        btnGuardar.setOnClickListener {
            val titulo = editTextTitulo.text.toString()
            val esPrivada = switchPrivada.isChecked
            if (titulo.isNotEmpty()) {
                val nuevaLista = Lista(titulo, esPrivada)

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val createdLista = popViewService.createLista(nuevaLista)
                        runOnUiThread {
                            val intent = Intent()
                            intent.putExtra("nuevaLista", createdLista)
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        runOnUiThread {
                            Toast.makeText(this@CrearListaActivity, "Error al crear la lista", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "El títol no pot estar buit.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
