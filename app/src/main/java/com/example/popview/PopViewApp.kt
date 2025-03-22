package com.example.popview

import android.app.Application
import android.provider.Settings
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
data class Statistics(
    var interacciones: Int = 0,
    var listasCreadas: Int = 0,
    var titulosCreados: Int = 0
)
class PopViewApp : Application() {
    companion object {
        var deviceId: String = ""
        const val appName = "PopView"
        const val appId = "popview"
        var estadistica = Statistics()
    }
    val db: FirebaseFirestore by lazy { Firebase.firestore }
    override fun onCreate() {
        super.onCreate()
        // Obtener el ID del dispositivo usando Settings.Secure (disponible desde API 1)
        //da error comentado
        /*deviceId = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        ) ?: "UNKNOWN_DEVICE"
        Log.d("PopViewApp", "Device ID: $deviceId")*/
        cargarDatosDesdeFirestore()
    }
    private fun cargarDatosDesdeFirestore() {
        //da error comentado de momento
        /*
        val doc = db.collection("Devices").document(deviceId)
        doc.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val estadisticaFirestore = documentSnapshot.toObject<Statistics>()
                    estadisticaFirestore?.let {
                        estadistica = it
                    }
                } else {
                    db.collection("Devices").document(deviceId).set(estadistica)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("PopViewApp", "Error al obtener datos de Firestore: $exception")
            }*/
    }
    fun guardarEstadisticas() {
        //da error comentado de momento
        /*
        db.collection("Devices").document(deviceId).set(estadistica)
            .addOnFailureListener {
                throw it
            }*/
    }
    fun resetEstadisticas() {
        estadistica = Statistics()
    }
}