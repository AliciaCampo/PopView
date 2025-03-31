package com.example.popview.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.popview.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.firestore.FirebaseFirestore
import com.github.mikephil.charting.formatter.ValueFormatter
import androidx.core.content.ContextCompat
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
        db.collection("Devices").get()
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

                    val comentariosCreados = document.getLong("comentarioCreado") ?: 0
                    val comentariosEliminados = document.getLong("comentarioEliminado") ?: 0
                    val comentariosEditados = document.getLong("comentarioEditado") ?: 0

                    // Gráfico de Barras (Listas)
                    barEntries.add(BarEntry(index.toFloat(), listasCreadas.toFloat()))
                    barEntries.add(BarEntry(index.toFloat() + 0.2f, listasEliminadas.toFloat()))
                    barEntries.add(BarEntry(index.toFloat() + 0.4f, listasEditadas.toFloat()))

                    // Gráfico de Pastel (Títulos)
                    pieEntries.add(PieEntry(titulosCreados.toFloat(), "Afegits"))
                    pieEntries.add(PieEntry(titulosEliminados.toFloat(), "Eliminats"))

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
        val dataSet = BarDataSet(entries, "")
        val colors = listOf(
            getColor(R.color.colorCreados),
            getColor(R.color.colorEliminados),
            getColor(R.color.colorEditados)
        )
        dataSet.colors = colors
        val data = BarData(dataSet)
        data.barWidth = 0.20f  // Reduce el ancho para dejar más espacio
        data.setValueTextSize(8f)
        barChart.data = data
        // Aumentar el espaciado entre barras
        val xAxis = barChart.xAxis
        xAxis.spaceMin = 1.5f  // Más espacio mínimo entre barras
        xAxis.spaceMax = 1.5f  // Más espacio máximo entre barras
        barChart.invalidate()  // Redibuja el gráfico
    }
    private fun updatePieChart(entries: List<PieEntry>) {
        // Filtrar entradas con valor 0 para que no se dibujen
        val filteredEntries = entries.filter { it.value > 0 }
        val total = filteredEntries.sumOf { it.value.toDouble() }
        if (total == 0.0) return // Evitar división por 0
        val normalizedEntries = filteredEntries.map {
            PieEntry((it.value / total * 100).toFloat(), it.label)
        }
        val dataSet = PieDataSet(normalizedEntries, "")
        dataSet.colors = listOf(getColor(R.color.colorCreados), getColor(R.color.colorEliminados))
        dataSet.setDrawValues(true) // Muestra solo los valores numéricos
        pieChart.setDrawEntryLabels(false) // Ocultar los textos dentro del gráfico
        val data = PieData(dataSet)
        data.setValueTextSize(14f)
        data.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "${value.toInt()}%" // Elimina los decimales
            }
        })
        pieChart.data = data
        // Configurar leyenda abajo
        pieChart.legend.isEnabled = true
        pieChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL
        pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        pieChart.invalidate() // Redibuja el gráfico
    }
    private fun updateLineChart(
        entriesCreados: List<Entry>,
        entriesEliminados: List<Entry>,
        entriesEditados: List<Entry>
    ) {
        val dataSetCreados = LineDataSet(entriesCreados, "Comentaris afegits").apply {
            color = ContextCompat.getColor(this@GraficosActivity, R.color.colorCreados)
            setCircleColor(color)
            setDrawCircles(true)
            setDrawValues(true)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            // Relleno semitransparente
            setDrawFilled(true)
            fillColor = ContextCompat.getColor(this@GraficosActivity, R.color.colorCreadosSemi)
        }
        val dataSetEliminados = LineDataSet(entriesEliminados, "Comentaris eliminats").apply {
            color = ContextCompat.getColor(this@GraficosActivity, R.color.colorEliminados)
            setCircleColor(color)
            setDrawCircles(true)
            setDrawValues(true)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawFilled(true)
            fillColor = ContextCompat.getColor(this@GraficosActivity, R.color.colorEliminadosSemi)
        }
        val dataSetEditados = LineDataSet(entriesEditados, "Comentaris modificats").apply {
            color = ContextCompat.getColor(this@GraficosActivity, R.color.colorEditados)
            setCircleColor(color)
            setDrawCircles(true)
            setDrawValues(true)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawFilled(true)
            fillColor = ContextCompat.getColor(this@GraficosActivity, R.color.colorEditadosSemi)
        }
        val data = LineData(dataSetCreados, dataSetEliminados, dataSetEditados)
        lineChart.data = data
        // Ajustar eje Y para que no termine en 0
        lineChart.axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = maxOf(
                dataSetCreados.yMax,
                dataSetEliminados.yMax,
                dataSetEditados.yMax
            ) + 2
        }
        lineChart.description.isEnabled = false
        lineChart.invalidate()
    }
}