package com.example.popview.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.popview.R
import com.google.android.material.chip.Chip

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextUserID = findViewById<EditText>(R.id.editTextUserID)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val inici = findViewById<Button>(R.id.inici)
        val registre = findViewById<Button>(R.id.registrar)
        val chipAjuda = findViewById<Chip>(R.id.chipAjuda)

        // Listener para el botón de inicio de sesión
        inici.setOnClickListener {
            val userID = editTextUserID.text.toString()
            val password = editTextPassword.text.toString()
            var hayError = false
            if (userID.isEmpty()) {
                editTextUserID.error = "L'ID d'usuari és obligatori"
                hayError = true
            }
            if (password.isEmpty()) {
                editTextPassword.error = "La contrasenya és obligatòria"
                hayError = true
            }
            if (hayError) return@setOnClickListener
            if (!isValidUser(userID, password)) {
                editTextPassword.error = "ID o contrasenya incorrectes"
                return@setOnClickListener
            }
            // Validar campos de texto
            if (userID.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Siusplau, escriu  l'usuari i la contrasenya", Toast.LENGTH_SHORT).show()
            } else {
                // Verificar las credenciales del usuario
                if (isValidUser(userID, password)) {
                    // Si las credenciales son correctas, ir a la actividad de inicio
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()  // Cerrar la actividad de login
                } else {
                    // Si las credenciales son incorrectas
                    Toast.makeText(this, "ID de l'usuari o contrasenya incorrectes", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Listener para el botón de registro
        registre.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        // Listener para el chip de ayuda
        chipAjuda.setOnClickListener {
            val intent = Intent(this, AjudaActivity::class.java)
            startActivity(intent)
        }
    }

    // Verificar el ID de usuario y la contraseña
    private fun isValidUser(userID: String, password: String): Boolean {
        // Este es solo un ejemplo con datos estáticos. Puedes sustituirlo con tu propia lógica.
        val validUserID = "user2131"
        val validPassword = "pirineus"
        return userID == validUserID && password == validPassword
    }
}
