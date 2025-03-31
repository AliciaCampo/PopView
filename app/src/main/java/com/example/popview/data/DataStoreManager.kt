package com.example.popview.data
import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import com.example.popview.PopViewApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
private val Context.dataStore by preferencesDataStore(name = "interacciones")
object DataStoreManager {
    private val INTERACCION_LISTA_CREADA = intPreferencesKey("interaccion_lista_creada")
    private val INTERACCION_LISTA_EDITADA = intPreferencesKey("interaccion_lista_editada")
    private val INTERACCION_LISTA_ELIMINADA = intPreferencesKey("interaccion_lista_eliminada")
    private val INTERACCION_TITULO_CREADO = intPreferencesKey("interaccion_titulo_creado")
    private val INTERACCION_TITULO_EDITADO = intPreferencesKey("interaccion_titulo_editado")
    private val INTERACCION_TITULO_ELIMINADO = intPreferencesKey("interaccion_titulo_eliminado")
    private val INTERACCION_COMENTARIO_CREADO = intPreferencesKey("interaccion_comentario_creado")
    private val INTERACCION_COMENTARIO_EDITADO = intPreferencesKey("interaccion_comentario_editado")
    private val INTERACCION_COMENTARIO_ELIMINADO = intPreferencesKey("interaccion_comentario_eliminado")
    private val db = FirebaseFirestore.getInstance()
    //  Guardar interacciones en DataStore y Firestore
    private suspend fun guardarInteraccion(context: Context, key: androidx.datastore.preferences.core.Preferences.Key<Int>, campoFirestore: String) {
        context.dataStore.edit { preferences ->
            val valorActual = preferences[key] ?: 0
            preferences[key] = valorActual + 1
        }
        actualizarFirestore(campoFirestore)
    }
    //  Actualizar Firestore en "Devices"
    private fun actualizarFirestore(campo: String) {
        val deviceRef = db.collection("Devices").document(PopViewApp.idDispositiu)
        deviceRef.update(campo, FieldValue.increment(1))
            .addOnSuccessListener { println(" Firestore actualizado: $campo +1") }
            .addOnFailureListener { println(" Error al actualizar Firestore en Devices") }
    }
    //  Métodos para guardar cada interacción
    suspend fun guardarListaCreada(context: Context) = guardarInteraccion(context, INTERACCION_LISTA_CREADA, "listasCreadas")
    suspend fun guardarListaEditada(context: Context) = guardarInteraccion(context, INTERACCION_LISTA_EDITADA, "listasEditadas")
    suspend fun guardarListaEliminada(context: Context) = guardarInteraccion(context, INTERACCION_LISTA_ELIMINADA, "listasEliminadas")

    suspend fun guardarTituloCreado(context: Context) = guardarInteraccion(context, INTERACCION_TITULO_CREADO, "titulosCreados")
    suspend fun guardarTituloEditado(context: Context) = guardarInteraccion(context, INTERACCION_TITULO_EDITADO, "titulosEditados")
    suspend fun guardarTituloEliminado(context: Context) = guardarInteraccion(context, INTERACCION_TITULO_ELIMINADO, "titulosEliminados")

    suspend fun guardarComentarioCreado(context: Context) = guardarInteraccion(context, INTERACCION_COMENTARIO_CREADO, "comentarioCreado")
    suspend fun guardarComentarioEditado(context: Context) = guardarInteraccion(context, INTERACCION_COMENTARIO_EDITADO, "comentarioEditado")
    suspend fun guardarComentarioEliminado(context: Context) = guardarInteraccion(context, INTERACCION_COMENTARIO_ELIMINADO, "comentarioEliminado")
    //  Obtener interacciones como Flow
    fun obtenerListaCreada(context: Context): Flow<Int> = context.dataStore.data.map { it[INTERACCION_LISTA_CREADA] ?: 0 }
    fun obtenerListaEditada(context: Context): Flow<Int> = context.dataStore.data.map { it[INTERACCION_LISTA_EDITADA] ?: 0 }
    fun obtenerListaEliminada(context: Context): Flow<Int> = context.dataStore.data.map { it[INTERACCION_LISTA_ELIMINADA] ?: 0 }

    fun obtenerTituloCreado(context: Context): Flow<Int> = context.dataStore.data.map { it[INTERACCION_TITULO_CREADO] ?: 0 }
    fun obtenerTituloEditado(context: Context): Flow<Int> = context.dataStore.data.map { it[INTERACCION_TITULO_EDITADO] ?: 0 }
    fun obtenerTituloEliminado(context: Context): Flow<Int> = context.dataStore.data.map { it[INTERACCION_TITULO_ELIMINADO] ?: 0 }

    fun obtenerComentarioCreado(context: Context): Flow<Int> = context.dataStore.data.map { it[INTERACCION_COMENTARIO_CREADO] ?: 0 }
    fun obtenerComentarioEditado(context: Context): Flow<Int> = context.dataStore.data.map { it[INTERACCION_COMENTARIO_EDITADO] ?: 0 }
    fun obtenerComentarioEliminado(context: Context): Flow<Int> = context.dataStore.data.map { it[INTERACCION_COMENTARIO_ELIMINADO] ?: 0 }
}