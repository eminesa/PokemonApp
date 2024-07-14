package com.eminesa.pokemonapp.model


import com.google.gson.annotations.SerializedName

data class VersionGroup(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)