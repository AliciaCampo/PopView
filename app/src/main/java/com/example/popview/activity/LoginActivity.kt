package com.example.popview.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.popview.R
import com.google.android.material.chip.Chip

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val inici = findViewById<Button>(R.id.inici)
        val registre = findViewById<Button>(R.id.registrar)
        val chipAjuda = findViewById<Chip>(R.id.chipAjuda)
        inici.setOnClickListener{
            val intent = Intent (this, HomeActivity::class.java)
            startActivity(intent)
        }
        registre.setOnClickListener{
            val intent = Intent (this, RegistroActivity::class.java)
            startActivity(intent)
        }
        chipAjuda.setOnClickListener {
            val intent = Intent(this, AjudaActivity::class.java)
            startActivity(intent)
        }

    }
}