package com.eminesa.pokemonapp.model


import com.google.gson.annotations.SerializedName

data class Cries(
    @SerializedName("latest")
    val latest: String?,
    @SerializedName("legacy")
    val legacy: String?
)