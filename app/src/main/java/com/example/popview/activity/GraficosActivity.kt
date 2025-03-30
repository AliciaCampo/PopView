package com.example.popview.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.popview.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.firestore.FirebaseFirestore

class GraficosActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart
    private lateinit var lineChart: LineChart

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graficos)

        barChart = findViewById(R.id.barChart)
        pieChart = findViewById(R.id.pieChart)
        lineChart = findViewById(R.id.lineChart)

        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        // Aquí agregas la lógica para obtener datos de Firebase Firestore
        db.collection("interacciones").get()
            .addOnSuccessListener { result ->
                val barEntries = mutableListOf<BarEntry>()
                val pieEntries = mutableListOf<PieEntry>()
                val lineEntriesCreados = mutableListOf<Entry>()
                val lineEntriesEliminados = mutableListOf<Entry>()
                val lineEntriesEditados = mutableListOf<Entry>()

                var index = 0
                result.forEach { document ->
                    val listasCreadas = document.getLong("listasCreadas") ?: 0
                    val listasEliminadas = document.getLong("listasEliminadas") ?: 0
                    val listasEditadas = document.getLong("listasEditadas") ?: 0

                    val titulosCreados = document.getLong("titulosCreados") ?: 0
                    val titulosEliminados = document.getLong("titulosEliminados") ?: 0

                    val comentariosCreados = document.getLong("comentariosCreados") ?: 0
                    val comentariosEliminados = document.getLong("comentariosEliminados") ?: 0
                    val comentariosEditados = document.getLong("comentariosEditados") ?: 0

                    // Gráfico de Barras (Listas)
                    barEntries.add(BarEntry(index.toFloat(), listasCreadas.toFloat()))
                    barEntries.add(BarEntry(index.toFloat() + 0.2f, listasEliminadas.toFloat()))
                    barEntries.add(BarEntry(index.toFloat() + 0.4f, listasEditadas.toFloat()))

                    // Gráfico de Pastel (Títulos)
                    pieEntries.add(PieEntry(titulosCreados.toFloat(), "Creados"))
                    pieEntries.add(PieEntry(titulosEliminados.toFloat(), "Eliminados"))

                    // Gráfico de Líneas (Comentarios)
                    lineEntriesCreados.add(Entry(index.toFloat(), comentariosCreados.toFloat()))
                    lineEntriesEliminados.add(Entry(index.toFloat(), comentariosEliminados.toFloat()))
                    lineEntriesEditados.add(Entry(index.toFloat(), comentariosEditados.toFloat()))

                    index++
                }

                // Actualizar gráficos con los datos obtenidos
                updateBarChart(barEntries)
                updatePieChart(pieEntries)
                updateLineChart(lineEntriesCreados, lineEntriesEliminados, lineEntriesEditados)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al cargar datos: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateBarChart(entries: List<BarEntry>) {
        val dataSet = BarDataSet(entries, "Listas")

        // Crear una lista con los colores que quieres para las barras
        val colors = listOf(
            getColor(R.color.colorCreados),
            getColor(R.color.colorEliminados),
            getColor(R.color.colorEditados)
        )

        // Usar la lista de colores para el DataSet
        dataSet.colors = colors

        val data = BarData(dataSet)
        barChart.data = data
        barChart.invalidate()  // Redibuja el gráfico
    }


    private fun updatePieChart(entries: List<PieEntry>) {
        val dataSet = PieDataSet(entries, "Títulos")
        dataSet.colors = listOf(getColor(R.color.colorCreados), getColor(R.color.colorEliminados))  // Colores personalizados
        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.invalidate()  // Redibuja el gráfico
    }

    private fun updateLineChart(
        entriesCreados: List<Entry>,
        entriesEliminados: List<Entry>,
        entriesEditados: List<Entry>
    ) {
        val dataSetCreados = LineDataSet(entriesCreados, "Comentarios Creados")
        dataSetCreados.color = getColor(R.color.colorCreados)  // Color para los comentarios creados

        val dataSetEliminados = LineDataSet(entriesEliminados, "Comentarios Eliminados")
        dataSetEliminados.color = getColor(R.color.colorEliminados)  // Color para los comentarios eliminados

        val dataSetEditados = LineDataSet(entriesEditados, "Comentarios Editados")
        dataSetEditados.color = getColor(R.color.colorEditados)  // Color para los comentarios editados

        val data = LineData(dataSetCreados, dataSetEliminados, dataSetEditados)
        lineChart.data = data
        lineChart.invalidate()  // Redibuja el gráfico
    }
}
