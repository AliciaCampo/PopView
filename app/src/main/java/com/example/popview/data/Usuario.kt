package com.example.popview.data

import com.google.gson.annotations.SerializedName


data class Usuario(
    val id: Int,
    @SerializedName("nom") val nombre: String,  // Usar @SerializedName para mapear el campo "nom" al campo "nombre"
    @SerializedName("imatge") val imagen: String,
    @SerializedName("edat") val edad: Int,
    @SerializedName("correu") val correo: String,
    @SerializedName("contrasenya") val contrasenya: String,
    val listas: List<Lista>? = null   // Asumiendo que Lista es otra clase
)