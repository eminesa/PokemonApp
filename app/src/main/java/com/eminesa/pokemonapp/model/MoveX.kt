package com.eminesa.pokemonapp.model


import com.google.gson.annotations.SerializedName

data class MoveX(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)