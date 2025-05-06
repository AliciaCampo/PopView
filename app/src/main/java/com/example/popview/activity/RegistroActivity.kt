package com.example.popview.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.popview.R
import com.example.popview.databinding.ActivityRegistroBinding
import com.example.popview.viewmodel.RegistroViewModel

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding

    // Usamos el ViewModel correcto
    private val registroViewModel: RegistroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botón de registro
        binding.buttonRegistre.setOnClickListener { registrarUsuario() }

        // Observamos los cambios en validación
        registroViewModel.validacionDatos.observe(this) { estado ->
            // Mostramos errores en cada TextInputLayout
            binding.textInputUsuario.error = estado.errorNombreUsuario
            binding.textInputEmail.error = estado.errorEmailUsuario
            binding.textInputEdad.error = estado.errorEdadUsuario
            binding.textInputContrasenya.error = estado.errorContrasenyaUsuario
            binding.textInputContrasenyaDos.error = estado.errorConfirmacionContrasenya
            // (Si quisieras mostrar mensaje de avatar, tendrías que usar un TextView o Toast)

            if (estado.esValido) {
                // Si todo es válido, seguimos al Login
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun registrarUsuario() {
        // Leemos todo como String
        val nombre = binding.textInputUsuario.editText?.text.toString()
        val email = binding.textInputEmail.editText?.text.toString()
        val edad = binding.textInputEdad.editText?.text.toString()
        val contrasenya = binding.textInputContrasenya.editText?.text.toString()
        val confirmacion = binding.textInputContrasenyaDos.editText?.text.toString()

        // Llamamos a validarDatosRegistro
        registroViewModel.validarDatosRegistro(
            nombre,
            email,
            edad,
            contrasenya,
            confirmacion,
            // avatar vendrá de registroViewModel.avatarSeleccionado
        )
    }
}
