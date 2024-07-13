package com.eminesa.pokemonapp.network

import com.eminesa.pokemonapp.model.PokemonDetail
import com.eminesa.pokemonapp.model.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //get pokemon name as a list
    @GET("pokemon-species/")
    suspend fun getPokemons(@Query("offset") offset: Int?, @Query("limit") limit: Int?): PokemonList

    //get pokemon detail by pokemon name
    @GET("pokemon-species/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String?): PokemonDetail
}