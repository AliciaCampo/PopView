package com.example.popview.data

import com.example.popview.data.Usuario
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Lista(
    @SerializedName("id") val id: Int=0,
    @SerializedName("titol") val titulo: String,
    @SerializedName("descripcio") val descripcion: String?,
    @SerializedName("privada") val esPrivada: Boolean,
    @SerializedName("usuari_id") val usuarioId: Int,
    @SerializedName("titols") val titulos: MutableList<Titulo> = mutableListOf()
) : Serializable

