package com.example.popview.activity

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.popview.R
import com.google.android.material.chip.Chip

class AjudaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajuda)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            finish() // Cierra esta actividad y regresa a la anterior
        }
        // Configurar el chip "Primers Passos"
        val chipPasos: Chip = findViewById(R.id.chipPasos)
        chipPasos.setOnClickListener {
            showPrimersPassosDialog()
        }

        // Configurar el chip "Centre d'Ajuda"
        val chipCentreAjuda: Chip = findViewById(R.id.chipCentreAjuda)
        chipCentreAjuda.setOnClickListener {
            showCentreAjudaDialog()
        }

        // Configurar el chip "Informació de l'aplicació"
        val chipInfoApp: Chip = findViewById(R.id.chipInfoApp)
        chipInfoApp.setOnClickListener {
            showInfoAppDialog()
        }
    }

    private fun showPrimersPassosDialog() {
        AlertDialog.Builder(this)
            .setTitle("Primers Passos")
            .setMessage(
                "Com a usuari, pots crear-les tu mateix utilitzant el botó que es troba a la teva pantalla d'usuari.\n\n" +
                        "Quan accedeixis a la pantalla d'usuari, veuràs un botó que et permetrà afegir una nova llista amb un sol clic. " +
                        "Un cop creada la llista, podràs afegir-hi elements des de diferents pantalles de l'aplicació.\n\n" +
                        "És fàcil i intuïtiu! Prova-ho ara."
            )
            .setPositiveButton("Entès") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    private fun showCentreAjudaDialog() {
        // Crear el diálogo para "Centre d'Ajuda"
        AlertDialog.Builder(this)
            .setTitle("Centre d'Ajuda")
            .setMessage("Telèfon: +34 123 456 789\n" +
                    "Correu electrònic: popwievhelp@gmail.com")
            .setPositiveButton("Tancar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showInfoAppDialog() {
        AlertDialog.Builder(this)
            .setTitle("Informació de l'aplicació")
            .setMessage(
                "Versió: 1.0.0\n" +
                        "Desenvolupadores:\n" +
                        "• Alicia Campo Miron\n" +
                        "• María Manzano Roldán\n" +
                        "• Judith Mera Aráez\n" +
                        "Data de llançament: Juliol 2025"
            )
            .setPositiveButton("Tancar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
