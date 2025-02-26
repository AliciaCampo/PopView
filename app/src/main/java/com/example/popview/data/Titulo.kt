package com.example.popview.data
import com.example.popview.data.Item
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Titulo(
    @SerializedName("imatge") val imagen: String,          // Mapea "imatge" del JSON a "imagen"
    @SerializedName("nom") val nombre: String,             // Mapea "nom" del JSON a "nombre"
    @SerializedName("descripcio") val description: String, // Mapea "descripcio" a "description"
    @SerializedName("genero") val genero: String?,         // Puede ser nulo, así que usa `String?`
    @SerializedName("edadRecomendada") val edadRecomendada: Int?, // Puede ser nulo, así que usa `Int?`
    @SerializedName("plataformes") val platforms: String,  // En el JSON es un `String`, no una lista
    @SerializedName("rating") val rating: Float,           // Puntuación
    @SerializedName("comentaris") val comments: List<Item>?, // Puede ser nulo, así que usa `List<Item>?`
    val id: Int = 0 // ID generado automáticamente por el servidor
) : Serializable
