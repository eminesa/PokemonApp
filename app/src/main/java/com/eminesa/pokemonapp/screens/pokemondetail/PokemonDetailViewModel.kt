package com.eminesa.pokemonapp.screens.pokemondetail

import android.graphics.Color
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

    fun getPokemonDetail(id: String?) {
        viewModelScope.launch {
            repository.getPokemonDetail(id).onEach { result ->
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

    fun setColor (versionName: String?): Int {
            val color = when (versionName) {
                "red" -> (Color.RED)
                "blue" -> (Color.BLUE)
                "yellow" -> (Color.YELLOW)
                "gold" -> (Color.parseColor("#FFD700"))
                "silver" -> (Color.parseColor("#C0C0C0"))
                "crystal" -> (Color.parseColor("#00FFFF"))
                "ruby" -> (Color.parseColor("#E0115F"))
                "sapphire" -> (Color.parseColor("#0F52BA"))
                "emerald" -> (Color.parseColor("#50C878"))
                "firered" -> (Color.parseColor("#FF4500"))
                "leafgreen" -> (Color.parseColor("#00FF00"))
                "diamond" -> (Color.parseColor("#B9F2FF"))
                "pearl" -> (Color.parseColor("#EAE0C8"))
                "platinum" -> (Color.parseColor("#E5E4E2"))
                "heartgold" -> (Color.parseColor("#D4AF37"))
                "soulsilver" -> (Color.parseColor("#C0C0C0"))
                else -> (Color.GREEN)
            }
            return color
        }


}