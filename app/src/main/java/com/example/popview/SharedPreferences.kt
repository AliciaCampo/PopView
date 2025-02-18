package com.example.popview

import android.content.Context
import com.example.popview.data.Lista
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun saveList(context: Context, lista: Lista) {
    val sharedPreferences = context.getSharedPreferences("listas", Context.MODE_PRIVATE)
    val gson = Gson()
    val listasJson = sharedPreferences.getString("listas_key", "[]")
    val listaType = object : TypeToken<MutableList<Lista>>() {}.type
    val listas: MutableList<Lista> = gson.fromJson(listasJson, listaType)

    listas.add(lista)  // Añadir la nueva lista

    val updatedListJson = gson.toJson(listas)
    sharedPreferences.edit().putString("listas_key", updatedListJson).apply()
}
fun loadLists(context: Context): MutableList<Lista> {
    val sharedPreferences = context.getSharedPreferences("listas", Context.MODE_PRIVATE)
    val gson = Gson()

    // Recuperar las listas guardadas de SharedPreferences
    val listasJson = sharedPreferences.getString("listas_key", "[]")
    val listaType = object : TypeToken<MutableList<Lista>>() {}.type
    return gson.fromJson(listasJson, listaType)
}
fun deleteList(context: Context, titulo: String) {
    val sharedPreferences = context.getSharedPreferences("listas", Context.MODE_PRIVATE)
    val gson = Gson()

    // Recuperar las listas guardadas de SharedPreferences
    val listasJson = sharedPreferences.getString("listas_key", "[]")
    val listaType = object : TypeToken<MutableList<Lista>>() {}.type
    val listas: MutableList<Lista> = gson.fromJson(listasJson, listaType)

    // Encontrar y eliminar la lista por título
    val listaToDelete = listas.find { it.titulo == titulo }
    if (listaToDelete != null) {
        listas.remove(listaToDelete)  // Eliminar la lista
        // Guardar la lista actualizada sin la lista eliminada
        val updatedListJson = gson.toJson(listas)
        sharedPreferences.edit().putString("listas_key", updatedListJson).apply()
    }
}

