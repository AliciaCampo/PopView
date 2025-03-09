package com.example.popview.activity

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.popview.R
import com.example.popview.data.Usuario
import com.example.popview.service.PopViewAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalleUsuarioActivity : AppCompatActivity() {
    private val popViewService = PopViewAPI().API()
    private var usuarioId: Int = -1
    private var imagenSeleccionada = "avataruser1" // Valor por defecto
    private lateinit var imageView: ImageView
    private val avatars = arrayOf(
        "avataruser1", "avataruser2", "avataruser3",
        "avataruser4", "avataruser5", "avataruser6"
    )
    private val avatarResources = mapOf(
        "avataruser1" to R.drawable.avataruser1,
        "avataruser2" to R.drawable.avataruser2,
        "avataruser3" to R.drawable.avataruser3,
        "avataruser4" to R.drawable.avataruser4,
        "avataruser5" to R.drawable.avataruser5,
        "avataruser6" to R.drawable.avataruser6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_usuario)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val checkboxMostrarContrasena = findViewById<CheckBox>(R.id.checkboxMostrarContrasena)

        // Establecer la acción para mostrar/ocultar la contraseña
        checkboxMostrarContrasena.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Mostrar la contraseña (texto visible)
                etContrasena.inputType = InputType.TYPE_CLASS_TEXT
            } else {
                // Ocultar la contraseña (texto en modo contraseña)
                etContrasena.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }

        // Botón para retroceder
        findViewById<ImageButton>(R.id.imageButtonEnrere).setOnClickListener {
            finish()
        }

        // ID de usuario recibido desde el intent
        usuarioId = intent.getIntExtra("usuarioId", -1)
        imageView = findViewById(R.id.imagenPerfil)

        if (usuarioId != -1) {
            cargarDatosUsuario(usuarioId)
        }

        // Selección de avatar al hacer clic en la imagen
        imageView.setOnClickListener {
            mostrarDialogoSeleccionAvatar()
        }

        // Guardar datos
        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            guardarDatosUsuario()
        }
    }

    // Cargar datos del usuario
    private fun cargarDatosUsuario(usuarioId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val usuario = popViewService.getUsuari(usuarioId)
                // Log para verificar todos los datos del usuario
                Log.d("DetalleUsuarioActivity", "Datos del usuario: $usuario")
                Log.d("DetalleUsuarioActivity", "Edad del usuario: ${usuario.edad}")
                runOnUiThread {
                    findViewById<EditText>(R.id.etNombre).setText(usuario.nombre)
                    findViewById<EditText>(R.id.etCorreo).setText(usuario.correo)
                    findViewById<EditText>(R.id.etEdad).setText(usuario.edad.toString())
                    findViewById<EditText>(R.id.etContrasena).setText(usuario.contrasenya)

                    // Cargar la imagen de perfil desde los recursos locales
                    imagenSeleccionada = usuario.imagen
                    cargarImagenPerfil(imagenSeleccionada)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("DetalleUsuarioActivity", "Error al obtener datos del usuario: ${e.message}")
            }
        }
    }



    // Mostrar diálogo para seleccionar avatar
    private fun mostrarDialogoSeleccionAvatar() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona una imagen de perfil")

        builder.setItems(avatars) { _, which ->
            imagenSeleccionada = avatars[which]
            cargarImagenPerfil(imagenSeleccionada)
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }
    // Cargar imagen de perfil desde los recursos locales
    private fun cargarImagenPerfil(nombreImagen: String) {
        val resourceId = avatarResources[nombreImagen] ?: R.drawable.avataruser1
        imageView.setImageResource(resourceId)
    }
    // Guardar datos del usuario
    private fun guardarDatosUsuario() {
        val nombre = findViewById<EditText>(R.id.etNombre).text.toString()
        val correo = findViewById<EditText>(R.id.etCorreo).text.toString()

        // Validar edad para evitar errores
        val edadText = findViewById<EditText>(R.id.etEdad).text.toString()

        // Verificar si la edad es válida
        val edad = if (edadText.isNotEmpty()) {
            val parsedEdad = edadText.toIntOrNull()
            if (parsedEdad != null && parsedEdad > 0) {
                parsedEdad
            } else {
                showError("Por favor, ingresa una edad válida (número positivo).")
                return
            }
        } else {
            showError("La edad no puede estar vacía")
            return
        }

        val contrasena = findViewById<EditText>(R.id.etContrasena).text.toString()

        // Validar si el correo y la contraseña no están vacíos
        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            showError("Por favor, completa todos los campos")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val usuarioActualizado = Usuario(
                    id = usuarioId,
                    nombre = nombre,
                    imagen = imagenSeleccionada,
                    edad = edad,
                    correo = correo,
                    contrasenya = contrasena
                )

                // Llamar a la API para actualizar el usuario
                val response = popViewService.updateUsuari(usuarioId, usuarioActualizado)

                // Si la actualización fue exitosa, cerrar la actividad
                if (response.isSuccessful) {
                    runOnUiThread {
                        finish()
                    }
                } else {
                    runOnUiThread {
                        showError("Error al actualizar los datos del usuario. Código: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    showError("Error de conexión o servidor")
                }
            }
        }
    }

    // Mostrar un mensaje de error
    private fun showError(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }


}
