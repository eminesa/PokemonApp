package com.eminesa.pokemonapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonDetail(
    @SerializedName("base_happiness")
    val base_happiness: Int? = null,

    @SerializedName("capture_rate")
    val capture_rate: Int? = null,

    @SerializedName("color")
    val color: PokemonList.Pokemon? = null,

    @SerializedName("egg_groups")
    val egg_groups: List<PokemonList.Pokemon?>? = null,

    @SerializedName("evolution_chain")
    val evolution_chain: EvolutionChain? = null,

    @SerializedName("evolves_from_species")
    val evolves_from_species: PokemonList.Pokemon? = null,

    @SerializedName("flavor_text_entries")
    val flavor_text_entries: List<FlavorTextEntries?>? = null,

    /*  @SerializedName("form_descriptions") Listenin icindeki model goruntulenmiyor
      val form_descriptions: List<>? = null, */

    @SerializedName("forms_switchable")
    val forms_switchable: Boolean? = null,

    @SerializedName("gender_rate")
    val gender_rate: Int? = null,

    @SerializedName("genera")
    val genera: List<Genera>? = null,

    @SerializedName("generation")
    val generation: PokemonList.Pokemon? = null,

    @SerializedName("growth_rate")
    val growth_rate: PokemonList.Pokemon? = null,

    @SerializedName("habitat")
    val habitat: PokemonList.Pokemon? = null,

    @SerializedName("has_gender_differences")
    val has_gender_differences: Boolean? = null,

    @SerializedName("hatch_counter")
    val hatch_counter: Int? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("is_baby")
    val is_baby: Boolean? = null,

    @SerializedName("is_legendary")
    val is_legendary: Boolean? = null,

    @SerializedName("is_mythical")
    val is_mythical: Boolean? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("names")
    val names: List<Names?>? = null,

    @SerializedName("order")
    val order: Int? = null,

    @SerializedName("pal_park_encounters")
    val pal_park_encounters: List<PalParkEncounters>? = null,

    @SerializedName("pokedex_numbers")
    val pokedex_numbers: List<PokedexNumbers>? = null,

    @SerializedName("varieties")
    val varieties: List<Varieties>? = null,

    @SerializedName("shape")
    val shape: PokemonList.Pokemon? = null,

    ) : Parcelable

@Parcelize
data class EvolutionChain(
    @SerializedName("url")
    val url: String? = null,
) : Parcelable


@Parcelize
data class FlavorTextEntries(
    @SerializedName("flavor_text")
    val flavor_text: String? = null,

    @SerializedName("language")
    val language: PokemonList.Pokemon? = null,

    @SerializedName("version")
    val version: PokemonList.Pokemon? = null,
) : Parcelable

@Parcelize
data class Genera(
    @SerializedName("genus")
    val genus: String? = null,

    @SerializedName("language")
    val language: PokemonList.Pokemon? = null,
) : Parcelable


@Parcelize
data class Names(
    @SerializedName("language")
    val language: PokemonList.Pokemon? = null,

    @SerializedName("name")
    val name: String? = null
) : Parcelable

@Parcelize
data class PokedexNumbers(
    @SerializedName("entry_number")
    val entry_number: Int? = null,

    @SerializedName("pokedex")
    val pokedex: PokemonList.Pokemon? = null,

    ) : Parcelable

@Parcelize
data class Varieties(
    @SerializedName("is_default")
    val is_default: Boolean? = null,

    @SerializedName("pokemon")
    val pokemon: PokemonList.Pokemon? = null,

    ) : Parcelable

@Parcelize
data class PalParkEncounters(
    @SerializedName("area")
    val area: PokemonList.Pokemon? = null,

    @SerializedName("base_score")
    val base_score: Int? = null,

    @SerializedName("rate")
    val rate: Int? = null
) : Parcelable