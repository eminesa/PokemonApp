package com.eminesa.pokemonapp.network

 import com.eminesa.pokemonapp.model.PokemonDetail
 import com.eminesa.pokemonapp.util.Resource
 import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemonDetail(name: String?): Flow<Resource<PokemonDetail>>
}