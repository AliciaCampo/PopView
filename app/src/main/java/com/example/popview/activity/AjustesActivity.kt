package com.example.popview.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.content.Intent
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.popview.R
import com.google.android.material.chip.Chip

class AjustesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes)

        val ajuda = findViewById<Chip>(R.id.chipAjuda)

        ajuda.setOnClickListener {
            val intent = Intent(this, AjudaActivity::class.java)
            startActivity(intent)
        }

        val reset = findViewById<Button>(R.id.buttonRestablir)

        reset.setOnClickListener {
            val sharedPreferences: SharedPreferences =
                getSharedPreferences("config", Context.MODE_PRIVATE)

            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            val spinnerIdioma = findViewById<Spinner>(R.id.spinnerIdioma)
            spinnerIdioma.setSelection(0)

            val spinnerMida = findViewById<Spinner>(R.id.spinnerMidaText)
            spinnerMida.setSelection(1)

            val spinnerHorarios = findViewById<Spinner>(R.id.spinnerHorari)
            spinnerHorarios.setSelection(13)

            val spinnerTema = findViewById<Spinner>(R.id.spinnerTema)
            spinnerTema.setSelection(0)
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        // Configurar el Spinner d'idioma
        val spinnerIdioma = findViewById<Spinner>(R.id.spinnerIdioma)
        val adapterIdioma = ArrayAdapter.createFromResource(
            this,
            R.array.idiomas_array,
            android.R.layout.simple_spinner_item
        )
        adapterIdioma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIdioma.adapter = adapterIdioma

        // Recuperar la selecció d'idioma guardada
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("config", Context.MODE_PRIVATE)
        val idiomaGuardado = sharedPreferences.getString("idiomaSeleccionado", "Español")
        val idiomaIndex = adapterIdioma.getPosition(idiomaGuardado)
        spinnerIdioma.setSelection(idiomaIndex)

        // Control de selecció en el Spinner d'idioma
        spinnerIdioma.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val idiomaSeleccionado = parent.getItemAtPosition(position).toString()

                // Guardar la selecció d'idioma
                val editor = sharedPreferences.edit()
                editor.putString("idiomaSeleccionado", idiomaSeleccionado)
                editor.apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Opcional: Que fer si no es selecciona res
            }
        }

        // Configurar el Spinner de mida (mides_array)
        val spinnerMida = findViewById<Spinner>(R.id.spinnerMidaText)
        val adapterMida = ArrayAdapter.createFromResource(
            this,
            R.array.mides_array,
            android.R.layout.simple_spinner_item
        )
        adapterMida.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMida.adapter = adapterMida

        // Recuperar la selecció de mida guardada
        val midaGuardada = sharedPreferences.getString("midaSeleccionada", "Mitjana")
        val midaIndex = adapterMida.getPosition(midaGuardada)
        spinnerMida.setSelection(midaIndex)

        // Control de selecció en el Spinner de mida
        spinnerMida.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val midaSeleccionada = parent.getItemAtPosition(position).toString()

                // Guardar la selecció de mida
                val editor = sharedPreferences.edit()
                editor.putString("midaSeleccionada", midaSeleccionada)
                editor.apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Opcional: Que fer si no es selecciona res
            }
        }

        // Configurar el Spinner de zones horàries (zonas_horarias_array)
        val spinnerHorarios = findViewById<Spinner>(R.id.spinnerHorari)
        val adapterHorarios = ArrayAdapter.createFromResource(
            this,
            R.array.zonas_horarias_array,
            android.R.layout.simple_spinner_item
        )
        adapterHorarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerHorarios.adapter = adapterHorarios

        // Recuperar la selecció de zona horària guardada
        val zonaGuardada = sharedPreferences.getString("zonaSeleccionada", "GMT")
        val zonaIndex = adapterHorarios.getPosition(zonaGuardada)
        spinnerHorarios.setSelection(zonaIndex)

        // Control de selecció en el Spinner de zona horària
        spinnerHorarios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val zonaSeleccionada = parent.getItemAtPosition(position).toString()

                // Guardar la selecció de zona horària
                val editor = sharedPreferences.edit()
                editor.putString("zonaSeleccionada", zonaSeleccionada)
                editor.apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Opcional: Que fer si no es selecciona res
            }
        }

        // Configurar el Spinner de temes
        val spinnerTema = findViewById<Spinner>(R.id.spinnerTema)
        val adapterTema = ArrayAdapter.createFromResource(
            this,
            R.array.tema_array,
            android.R.layout.simple_spinner_item
        )
        adapterTema.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTema.adapter = adapterTema

        // Recuperar la selecció de tema guardada
        val temaGuardado = sharedPreferences.getString("temaSeleccionado", "Claro")
        val temaIndex = adapterTema.getPosition(temaGuardado)
        spinnerTema.setSelection(temaIndex)

        // Control de selecció en el Spinner de tema
        spinnerTema.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val temaSeleccionado = parent.getItemAtPosition(position).toString()

                // Guardar la selecció de tema
                val editor = sharedPreferences.edit()
                editor.putString("temaSeleccionado", temaSeleccionado)
                editor.apply()

                // Aplicar el tema a l'aplicació
                when (temaSeleccionado) {
                    "Claro" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    "Oscuro" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Opcional: Que fer si no es selecciona res
            }
        }
        //boton mostrar graficos
        val buttonGraficos = findViewById<Button>(R.id.buttonGraficos)
        buttonGraficos.setOnClickListener {
            val intent = Intent(this, GraficosActivity::class.java)
            startActivity(intent)
        }
        // Control dels marges dels costats de la pantalla (Edge to Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Quan es reinicia l'activitat, es recuperen les seleccions guardades
    override fun onResume() {
        super.onResume()
        // Recuperar les seleccions guardades
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("config", Context.MODE_PRIVATE)
        val idiomaGuardado = sharedPreferences.getString("idiomaSeleccionado", "Español")
        val midaGuardada = sharedPreferences.getString("midaSeleccionada", "Mitjana")
        val zonaGuardada = sharedPreferences.getString("zonaSeleccionada", "GMT")
        val temaGuardado = sharedPreferences.getString("temaSeleccionado", "Claro")
    }
}
