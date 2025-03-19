package com.example.popview.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.popview.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class GraficosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graficos)

        // Referencia al BarChart
        val barChart = findViewById<BarChart>(R.id.barChart)

        // Datos de ejemplo
        val entries = listOf(
            BarEntry(1f, 10f),
            BarEntry(2f, 20f),
            BarEntry(3f, 15f),
            BarEntry(4f, 25f),
            BarEntry(5f, 5f)
        )

        // Configuración del conjunto de datos
        val dataSet = BarDataSet(entries, "Interacciones")
        dataSet.color = Color.parseColor("#6200EE")
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 12f

        // Asignar datos al gráfico
        val barData = BarData(dataSet)
        barChart.data = barData

        // Configurar el gráfico
        barChart.description.text = "Interacciones por día"
        barChart.description.textColor = Color.WHITE
        barChart.setBackgroundColor(Color.BLACK)
        barChart.animateY(1000)

        // Mostrar los datos
        barChart.invalidate()
    }
}
