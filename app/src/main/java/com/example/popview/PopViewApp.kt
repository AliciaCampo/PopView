import android.app.Application
import com.google.firebase.FirebaseApp

import android.provider.Settings
import android.util.Log
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
        // Cambiado de val a var para que puedas reasignarlo
        var deviceId: String = ""
        const val APP_NAME = "PopView"
        var statistics = Statistics()
    }

    // Inicialización diferida de Firestore
    val db: FirebaseFirestore by lazy { Firebase.firestore }

    override fun onCreate() {
        super.onCreate()

        // Usar Settings.Secure.ANDROID_ID en lugar de getDeviceId()
        deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        // Leer datos desde Firestore
        val doc = db.collection("Devices").document(deviceId)
        doc.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val statsFromDB = document.toObject<Statistics>()
                    if (statsFromDB != null) {
                        statistics = statsFromDB
                    }
                } else {
                    // Si no existe, guarda los valores por defecto
                    db.collection("Devices").document(deviceId).set(statistics)
                }
            }
            .addOnFailureListener { e ->
                Log.e("PopViewApp", "Error al leer estadísticas", e)
            }
    }

    fun saveStats() {
        db.collection("Devices").document(deviceId).set(statistics)
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
