package com.example.popview.data

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
// Crear instancia de DataStore
private val Context.dataStore by preferencesDataStore(name = "settings")
object DataStoreManager {
    private val INTERACCIONES = intPreferencesKey("interacciones")
    // Guardar interacciones
    suspend fun guardarInteraccion(context: Context) {
        context.dataStore.edit { settings ->
            val current = settings[INTERACCIONES] ?: 0
            settings[INTERACCIONES] = current + 1
        }
    }
    // Obtener el valor almacenado
    fun obtenerInteraccion(context: Context): Int {
        return runBlocking {
            context.dataStore.data.first()[INTERACCIONES] ?: 0
        }
    }
}