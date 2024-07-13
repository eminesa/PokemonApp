package com.eminesa.pokemonapp.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eminesa.pokemonapp.model.PokemonList
import com.eminesa.pokemonapp.screens.pokemons.PokemonsViewModel
import java.lang.ref.WeakReference

class PokemonPaginationSource(pokemonViewModel: PokemonsViewModel) :
    PagingSource<Int, PokemonList.Pokemon>() {

    private val viewModel = WeakReference(pokemonViewModel).get()

    override fun getRefreshKey(state: PagingState<Int, PokemonList.Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(20)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(20)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonList.Pokemon> {
        return try {
            val offset = if (viewModel?.isRefreshing == false)
                viewModel.itemCount ?: 0
            else
                0

            val limit = params.loadSize

            val response =
                viewModel?.repository?.getPokemonList(offsetSize = offset, limitSize = limit)

            val pokemonList: MutableList<PokemonList.Pokemon> =
                response?.results?.toMutableList() ?: mutableListOf()

            viewModel?.itemCount = if (pokemonList.isEmpty()) {
                null
            } else {
                (viewModel?.itemCount?.plus(pokemonList.size))
            }

            //every url will be special
            if (viewModel?.pokemonId == pokemonList.lastOrNull()?.url.toString()) {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            } else {
                viewModel?.pokemonId = pokemonList.lastOrNull()?.url.toString()
                LoadResult.Page(
                    data = pokemonList,
                    prevKey = null,
                    nextKey = viewModel?.itemCount
                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}