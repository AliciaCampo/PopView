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
    var totalComments: Int = 0,
    var editedComments: Int = 0,
    var deletedComments: Int = 0
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
                } else {
                    // Si no existe, guarda los valores por defecto
                    db.collection("Devices").document(idDispositiu).set(idDispositiu)
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