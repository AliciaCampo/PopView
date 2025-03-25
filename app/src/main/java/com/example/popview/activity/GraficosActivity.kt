package com.example.popview.activity
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.popview.R
import com.example.popview.data.DataStoreManager
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
class GraficosActivity : AppCompatActivity() {
    private lateinit var barChart: BarChart
    private val firestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graficos)
        barChart = findViewById(R.id.barChart)
        // Guardar interacciones con DataStore
        lifecycleScope.launch {
            // Guardamos interacciones en DataStore
            DataStoreManager.guardarInteraccionLista(this@GraficosActivity)
            DataStoreManager.guardarInteraccionTitulo(this@GraficosActivity)
            DataStoreManager.guardarInteraccionComentario(this@GraficosActivity)
            // Recuperamos los valores de DataStore
            val interaccionesLista = DataStoreManager.obtenerInteraccionLista(this@GraficosActivity).first()
            val interaccionesTitulo = DataStoreManager.obtenerInteraccionTitulo(this@GraficosActivity).first()
            val interaccionesComentario = DataStoreManager.obtenerInteraccionComentario(this@GraficosActivity).first()
            // Mostramos el gráfico con los datos de DataStore
            mostrarGrafico(interaccionesLista, interaccionesTitulo, interaccionesComentario)
            // Recuperar datos desde Firebase y actualizar gráfico
            cargarDatosDesdeFirebase()
        }
    }
    private fun mostrarGrafico(interaccionesLista: Int, interaccionesTitulo: Int, interaccionesComentario: Int) {
        val entries = mutableListOf<BarEntry>()
        // Añadir datos de DataStore para las interacciones
        entries.add(BarEntry(1f, interaccionesLista.toFloat()))
        entries.add(BarEntry(2f, interaccionesTitulo.toFloat()))
        entries.add(BarEntry(3f, interaccionesComentario.toFloat()))
        val dataSet = BarDataSet(entries, "Interacciones")
        dataSet.color = Color.parseColor("#6200EE")
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 12f
        val barData = BarData(dataSet)
        barChart.data = barData
        // Personalización del gráfico
        barChart.description.text = "Interacciones por actividad"
        barChart.description.textColor = Color.WHITE
        barChart.setBackgroundColor(Color.BLACK)
        barChart.animateY(1000)
        barChart.invalidate()
    }
    private fun cargarDatosDesdeFirebase() {
        firestore.collection("interacciones")
            .get()
            .addOnSuccessListener { result ->
                val entries = mutableListOf<BarEntry>()
                // Añadir datos de Firebase al gráfico
                var index = 4f
                for (document in result) {
                    val valor = document.getLong("valor")?.toFloat() ?: 0f
                    entries.add(BarEntry(index, valor))
                    index++
                }
                // Actualizar gráfico con datos de Firebase
                if (entries.isNotEmpty()) {
                    val dataSet = BarDataSet(entries, "Datos de Firebase")
                    dataSet.color = Color.parseColor("#03DAC5")
                    dataSet.valueTextColor = Color.WHITE
                    dataSet.valueTextSize = 12f

                    val barData = barChart.data
                    barData.addDataSet(dataSet)

                    barChart.data = barData
                    barChart.invalidate()
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}