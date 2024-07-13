package com.eminesa.pokemonapp.screens.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eminesa.pokemonapp.network.PokemonDetailRepositoryImpl
import com.eminesa.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repository: PokemonDetailRepositoryImpl) :
    ViewModel() {

    private val _state = MutableStateFlow<PokemonDetailViewState>(PokemonDetailViewState.Init)
    fun getViewState(): StateFlow<PokemonDetailViewState> = _state.asStateFlow()

    fun setLoading(isLoading: Boolean) {
        _state.value = PokemonDetailViewState.Loading(isLoading)
    }

    fun getPokemonDetail(name: String?) {
        viewModelScope.launch {
            repository.getPokemonDetail(name).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        setLoading(false)
                        _state.value = PokemonDetailViewState.Error(result.message)
                    }
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        if (result.data == null) {
                            _state.value = PokemonDetailViewState.SuccessWithEmptyData
                        } else {
                            _state.value = PokemonDetailViewState.Success(result.data)
                        }
                    }
                }
            }.launchIn(this)
        }
    }

}