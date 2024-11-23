package com.example.popview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ValoracionTituloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_valoracion_titulo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton imageButtonEnrere = findViewById(R.id.imageButtonEnrere);
        // Configurar botón de "enrere" para ir a BuscarActivity
        imageButtonEnrere.setOnClickListener(v -> {
            // Crear Intent para ir a BuscarActivity
            Intent intentEnrere = new Intent(ValoracionTituloActivity.this, BuscarActivity.class);
            startActivity(intentEnrere);
            // Opcional: cerrar la actividad actual si no quieres que el usuario vuelva
            finish();
        });
    }
}