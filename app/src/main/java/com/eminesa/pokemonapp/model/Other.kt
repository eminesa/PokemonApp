package com.eminesa.pokemonapp.model


import com.google.gson.annotations.SerializedName

data class Other(
    @SerializedName("dream_world")
    val dreamWorld: DreamWorld?,
    @SerializedName("home")
    val home: Home?,
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork?,
    @SerializedName("showdown")
    val showdown: Showdown?
)