package com.eminesa.pokemonapp.screens.pokemondetail

import com.eminesa.pokemonapp.model.PokemonDetail
import com.eminesa.pokemonapp.util.UiText


sealed class PokemonDetailViewState {
    object Init : PokemonDetailViewState()
    data class Loading(val isLoading: Boolean) : PokemonDetailViewState()
    data class Success(val data: PokemonDetail) : PokemonDetailViewState()
    object SuccessWithEmptyData : PokemonDetailViewState()
    data class Error(val error: UiText) : PokemonDetailViewState()
}