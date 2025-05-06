package com.example.popview.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.popview.R
import com.example.popview.databinding.ActivityRegistroBinding
import com.example.popview.viewmodel.UsuarioViewModel

class RegistroActivity : AppCompatActivity() {
    // Usamos el ViewBinding para acceder a las vistas de manera más sencilla
    lateinit var binding: ActivityRegistroBinding
    // Instanciamos el ViewModel de la actividad
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflamos el layout con ViewBinding
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Configuramos el botón de registro para que cuando se haga clic, ejecute la validación
        binding.buttonRegistre.setOnClickListener { registrarUsuario() }
        // Observar el estado de la validación de los datos
        usuarioViewModel.validacionDatos.observe(this) { estado ->
            // Si los datos no son válidos, mostramos los errores
            binding.textInputUsuario.error = estado.errorNombreUsuario
            binding.textInputEmail.error = estado.errorEmailUsuario
            binding.textInputEdad.error = estado.errorEdadUsuario
            binding.textInputContrasenya.error = estado.errorContrasenyaUsuario
            // Si todos los datos son válidos, redirigimos al LoginActivity
            if (estado.esValido) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun registrarUsuario() {
        // Obtenemos los datos de los campos
        val nombre = binding.textInputUsuario.editText?.text.toString()
        val email = binding.textInputEmail.editText?.text.toString()
        val edad = binding.textInputEdad.editText?.text.toString().toIntOrNull()
        val contrasenya = binding.textInputContrasenya.editText?.text.toString()

        // Llamamos al ViewModel para validar los datos
        usuarioViewModel.validarDatosUsuario(nombre, email, edad, contrasenya)
    }
}
