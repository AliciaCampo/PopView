package com.example.popview.data

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Crear instancia de DataStore
private val Context.dataStore by preferencesDataStore(name = "interacciones")

object DataStoreManager {

    // Clave para almacenar el valor de interacciones
    private val INTERACCION_LISTA = intPreferencesKey("interaccion_lista")
    private val INTERACCION_TITULO = intPreferencesKey("interaccion_titulo")
    private val INTERACCION_COMENTARIO = intPreferencesKey("interaccion_comentario")

    //  Guardar interacción para listas
    suspend fun guardarInteraccionLista(context: Context) {
        context.dataStore.edit { preferences ->
            val valorActual = preferences[INTERACCION_LISTA] ?: 0
            preferences[INTERACCION_LISTA] = valorActual + 1
        }
    }

    //  Guardar interacción para títulos
    suspend fun guardarInteraccionTitulo(context: Context) {
        context.dataStore.edit { preferences ->
            val valorActual = preferences[INTERACCION_TITULO] ?: 0
            preferences[INTERACCION_TITULO] = valorActual + 1
        }
    }

    //  Guardar interacción para comentarios
    suspend fun guardarInteraccionComentario(context: Context) {
        context.dataStore.edit { preferences ->
            val valorActual = preferences[INTERACCION_COMENTARIO] ?: 0
            preferences[INTERACCION_COMENTARIO] = valorActual + 1
        }
    }

    //  Obtener interacción de listas como Flow
    fun obtenerInteraccionLista(context: Context): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[INTERACCION_LISTA] ?: 0
        }
    }

    //  Obtener interacción de títulos como Flow
    fun obtenerInteraccionTitulo(context: Context): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[INTERACCION_TITULO] ?: 0
        }
    }

    //  Obtener interacción de comentarios como Flow
    fun obtenerInteraccionComentario(context: Context): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[INTERACCION_COMENTARIO] ?: 0
        }
    }
}
