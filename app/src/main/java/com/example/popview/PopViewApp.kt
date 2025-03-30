package com.example.popview
import android.app.Application
import android.provider.Settings
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
data class Statistics(
    var comentarioCreado : Int = 0,
    var comentarioEditado : Int = 0,
    var comentarioEliminado : Int = 0,
    var listasCreadas: Int = 0,
    var listasEditadas: Int = 0,
    var listasEliminadas: Int = 0,
    var titulosCreados: Int = 0,
    var titulosEditados: Int = 0,
    var titulosEliminados: Int = 0
)
class PopViewApp : Application() {
    companion object {
        var idDispositiu = ""
        const val APP_NAME = "PopView"
        var statistics = Statistics()
    }
    // Inicialización diferida de Firestore
    val db: FirebaseFirestore by lazy { Firebase.firestore }
    override fun onCreate() {
        super.onCreate()
        idDispositiu = Settings.Secure.getString(
            getApplicationContext().contentResolver,
            Settings.Secure.ANDROID_ID
        )
        // Leer datos desde Firestore
        val doc = db.collection("Devices").document(idDispositiu)
        doc.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val statsFromDB = document.toObject<Statistics>()
                    PopViewApp.statistics = statsFromDB!!
                    Log.i("PopViewApp", "Estadísticas recuperadas correctamente")
                } else {
                    // Si no existe, guarda los valores por defecto
                    Log.w("PopViewApp", "Documento no encontrado, creando uno nuevo")
                    db.collection("Devices").document(idDispositiu).set(mapOf("id" to idDispositiu))

                }
            }
            .addOnFailureListener { e ->
                Log.e("PopViewApp", "Error al leer estadísticas", e)
            }
    }
    fun saveStats() {
        db.collection("Devices").document(idDispositiu).set(statistics)
            .addOnSuccessListener {
                Log.i("PopViewApp", "Estadísticas guardadas correctamente")
            }
            .addOnFailureListener {
                Log.e("PopViewApp", "Error al guardar estadísticas", it)
            }
    }
    fun resetStats() {
        statistics = Statistics()
    }
}