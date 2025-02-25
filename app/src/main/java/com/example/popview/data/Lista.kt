package com.example.popview.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Lista(
    @SerializedName("id") val id: Int=0,  // Asegúrate de que este campo exista en la respuesta de la API
    @SerializedName("titol") val titulo: String,
    @SerializedName("descripcio") val descripcion: String?,
    @SerializedName("privada") val esPrivada: Boolean,
    @SerializedName("titols") val titulos: MutableList<Titulo> = mutableListOf()
) : Serializable

