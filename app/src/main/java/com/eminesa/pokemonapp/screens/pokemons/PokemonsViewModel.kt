package com.eminesa.pokemonapp.screens.pokemons

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eminesa.pokemonapp.model.PokemonList
import com.eminesa.pokemonapp.network.PokemonRepositoryImpl
import com.eminesa.pokemonapp.pagination.PokemonListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PokemonsViewModel @Inject constructor(val repository: PokemonRepositoryImpl) : ViewModel() {


    var itemCount: Int? = 0
    var recyclerViewPosition: Int = 0
    var isRefreshing = false
    var pokemon = "poke"
    var pokemonId: String = ""

    var hashMapPokemon: HashMap<String, MutableLiveData<PagingData<PokemonList.Pokemon>>?> =
        HashMap()

    private var pokemonList: LiveData<PagingData<PokemonList.Pokemon>>? =
        null

    val pokemonListWithLiveData: LiveData<PagingData<PokemonList.Pokemon>>?
        get() = pokemonList

    fun launchPagingAsync() {
        viewModelScope.launch {
            try {
                if (hashMapPokemon[pokemon] == null)
                    hashMapPokemon[pokemon] =
                        PokemonListRepository().getServiceResultStream(this@PokemonsViewModel)
                            .asLiveData(Dispatchers.Default + viewModelScope.coroutineContext)
                            .cachedIn(viewModelScope) as MutableLiveData

                hashMapPokemon[pokemon]?.let {
                    pokemonList = it
                }

            } catch (ex: Exception) {
                println("Error ${ex.localizedMessage}")
            }
        }

    }

    fun clearData() {
        hashMapPokemon.remove(pokemon)
    }

    override fun onCleared() {
        clearData()
        super.onCleared()
    }
}

