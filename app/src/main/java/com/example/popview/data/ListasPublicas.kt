package com.example.popview.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ListaPublica(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("titol") val titol: String = "",
    @SerializedName("descripcio") val descripcion: String? = null,
    @SerializedName("privada") val esPrivada: Boolean = false,
    @SerializedName("titols") val titulos: List<String> = emptyList() // Cambiado a List<String>
) : Serializable