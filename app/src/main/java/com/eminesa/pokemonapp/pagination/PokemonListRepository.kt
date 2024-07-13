package com.eminesa.pokemonapp.pagination

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eminesa.pokemonapp.model.PokemonList
import com.eminesa.pokemonapp.screens.pokemons.PokemonsViewModel
import kotlinx.coroutines.flow.Flow

/**
 * Bu class olusturulan PaginationSource'lere ve servis uzerinden gelen veriye ulaşmayı saglar.
 */
class PokemonListRepository {
    fun getServiceResultStream(
        viewModel: PokemonsViewModel
    ): Flow<PagingData<PokemonList.Pokemon>> {
        return Pager(
            PagingConfig(
                pageSize = POKEMON_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = POKEMON_PAGE_SIZE,
                prefetchDistance = 10,
            )
        ) {
            PokemonPaginationSource(viewModel)
        }.flow
    }

    companion object {
        const val POKEMON_PAGE_SIZE = 20
    }
}