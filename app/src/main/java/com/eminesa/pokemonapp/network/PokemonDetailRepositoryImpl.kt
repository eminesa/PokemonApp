package com.eminesa.pokemonapp.network

import com.eminesa.pokemonapp.R
import com.eminesa.pokemonapp.extentions.handleError
import com.eminesa.pokemonapp.model.PokemonDetail
import com.eminesa.pokemonapp.util.Resource
import com.eminesa.pokemonapp.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

//@Inject annotasyonu bağımlılıkları bağımlı sınıflarımıza enjekte etmek içi kullanırız.
class PokemonDetailRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PokemonRepository {

    override suspend fun getPokemonDetail(name: String?): Flow<Resource<PokemonDetail>> = flow {
        try {
            emit(Resource.Loading())

            val response = apiService.getPokemonDetail(name = name)

            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.could_not_reach_server_error)))
        }
    }
}