package com.eminesa.pokemonapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PokemonList(
    @SerializedName("count")
    val count: Int? = null,

    @SerializedName("next")
    val next: String? = null,

    @SerializedName("previous")
    val previous: String? = null,

    @SerializedName("results")
    val results: List<Pokemon>? = null,

    ) : Parcelable {

    @Parcelize
    data class Pokemon(

        @SerializedName("name")
        val name: String? = null,

        @SerializedName("url")
        val url: String? = null,

        ) : Parcelable
}



