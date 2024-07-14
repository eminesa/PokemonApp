package com.eminesa.pokemonapp.network

import com.eminesa.pokemonapp.model.PokemonList
import javax.inject.Inject

//@Inject annotasyonu bağımlılıkları bağımlı sınıflarımıza enjekte etmek içi kullanırız.
class PokemonRepositoryImpl @Inject constructor(
    private val api: ApiService,
) {
    suspend fun getPokemonList(offsetSize: Int?, limitSize: Int?): PokemonList =
        api.getPokemonList(offset = offsetSize, limit = limitSize)
}