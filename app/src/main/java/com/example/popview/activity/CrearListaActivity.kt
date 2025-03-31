package com.example.popview.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.popview.PopViewApp
import com.example.popview.data.Lista
import com.example.popview.R
import com.example.popview.data.DataStoreManager
import com.example.popview.service.PopViewAPI
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
class CrearListaActivity : AppCompatActivity() {
    private val popViewService = PopViewAPI().API()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_lista)
        // Obtener el ID del usuario desde el Intent
        val usuarioId = intent.getIntExtra("usuarioId", -1)
        // Validar que el ID del usuario es válido
        if (usuarioId == -1) {
            Toast.makeText(this, "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show()
            finish()  // Finalizar la actividad si no se pasa el ID de usuario
            return
        }
        val editTextTitulo = findViewById<EditText>(R.id.editTextTitulo)
        val switchPrivada = findViewById<Switch>(R.id.switchPrivada)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val imageButtonEnrere: ImageButton = findViewById(R.id.imageButtonEnrere)
        // Regresar a la actividad anterior
        imageButtonEnrere.setOnClickListener {
            finish()
        }
        // Al hacer clic en "Guardar"
        btnGuardar.setOnClickListener {
            val titulo = editTextTitulo.text.toString()
            val esPrivada = switchPrivada.isChecked
            if (titulo.isNotEmpty()) {
                val nuevaLista = Lista(
                    titulo = titulo,
                    descripcion = null,  // Puedes ajustar esto si deseas permitir una descripción
                    esPrivada = esPrivada,
                    titulos = mutableListOf(),
                    usuarioId = usuarioId  // Pasamos el ID del usuario al crear la lista
                )
                // Usar CoroutineScope para realizar la petición en un hilo en segundo plano
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Crear la lista usando el servicio
                        val createdLista = popViewService.createLista(nuevaLista)
                        runOnUiThread {
                            val intent = Intent()
                            intent.putExtra("nuevaLista", createdLista)  // Pasar la lista creada
                            setResult(RESULT_OK, intent)
                            crearLista(titulo)
                            finish()  // Finalizar la actividad
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        runOnUiThread {
                            Toast.makeText(this@CrearListaActivity, "Error al crear la lista: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "El título no puede estar vacío.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun registrarInteraccionEnDevices() {
        val db = FirebaseFirestore.getInstance()
        val deviceRef = db.collection("Devices").document(PopViewApp.idDispositiu)

        // Incrementar en +1 el contador de listas creadas
        deviceRef.update("listasCreadas", FieldValue.increment(1))
            .addOnSuccessListener {
                Log.d("PopViewApp", "Interacción guardada en Devices correctamente")
            }
            .addOnFailureListener { e ->
                Log.e("PopViewApp", "Error al actualizar estadísticas en Devices", e)
            }
    }

    // Crear lista
    private fun crearLista(nombre: String) {
        // Lógica para crear una lista
        lifecycleScope.launch {
            DataStoreManager.guardarInteraccionLista(this@CrearListaActivity)
            registrarInteraccionEnDevices()
        }
    }
}