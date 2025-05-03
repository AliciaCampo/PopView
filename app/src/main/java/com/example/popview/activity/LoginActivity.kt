package com.example.popview.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.popview.R
import com.example.popview.viewmodel.LoginViewModel
import com.google.android.material.chip.Chip

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextUserID    = findViewById<EditText>(R.id.editTextUserID)
        val editTextPassword  = findViewById<EditText>(R.id.editTextPassword)
        val inici             = findViewById<Button>(R.id.inici)
        val registre          = findViewById<Button>(R.id.registrar)
        val chipAjuda         = findViewById<Chip>(R.id.chipAjuda)

        // 1) Observamos los cambios de validación del ViewModel
        viewModel.loginState.observe(this, Observer { state ->
            // Limpiamos errores previos
            editTextUserID.error   = null
            editTextPassword.error = null

            // Si no es válido, mostramos errores
            if (!state.esValido) {
                state.errorUserID?.let { editTextUserID.error = it }
                state.errorPassword?.let { editTextPassword.error = it }
            } else {
                // Login OK → navegamos y cerramos
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        })

        // 2) Al pulsar “inici” pedimos al ViewModel validar
        inici.setOnClickListener {
            val userID   = editTextUserID.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            viewModel.validar(userID, password)
        }

        // 3) Botón registrar y chip ayuda igual que antes
        registre.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
        chipAjuda.setOnClickListener {
            startActivity(Intent(this, AjudaActivity::class.java))
        }
    }
}
