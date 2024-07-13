package com.eminesa.pokemonapp.screens

import com.eminesa.pokemonapp.model.PokemonDetail
import com.eminesa.pokemonapp.network.PokemonDetailRepositoryImpl
import com.eminesa.pokemonapp.screens.pokemondetail.PokemonDetailViewModel
import com.eminesa.pokemonapp.screens.pokemondetail.PokemonDetailViewState
import com.eminesa.pokemonapp.util.Resource
import com.eminesa.pokemonapp.util.UiText
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PokemonDetailViewModelTest {

    private val name: String = "Pikachu"
    private val errorMessage: String = "error"

    private val pokemonDetail = PokemonDetail(
        name = "Pikachu",
        id = 3,
    )

    @Mock
    private lateinit var repository: PokemonDetailRepositoryImpl

    private lateinit var viewModel: PokemonDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = PokemonDetailViewModel(repository)
    }

    @Test
    fun `getPokemonDetailRepositoryImpl emits success`() = runTest {
        whenever(repository.getPokemonDetail(any())).thenAnswer {
            flow { emit(Resource.Success(data =  pokemonDetail)) }
        }

        viewModel.getPokemonDetail(name)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(PokemonDetailViewState.Success::class.java)
    }

    @Test
    fun `getPokemonDetailRepositoryImpl emits error`() = runTest {
        whenever(repository.getPokemonDetail(any())).thenAnswer {
            flow<Resource<Any>> { emit(Resource.Error(message = UiText.DynamicString(value = errorMessage))) }
        }
        viewModel.getPokemonDetail(name)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(PokemonDetailViewState.Error::class.java)
    }

    @Test
    fun `getPokemonDetailRepositoryImpl emits loading`() = runTest {
        whenever(repository.getPokemonDetail(any())).thenAnswer {
            flow<Resource<Any>> { emit(Resource.Loading()) }
        }
        viewModel.getPokemonDetail(name)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(PokemonDetailViewState.Loading::class.java)
    }

    @Test
    fun `verify getPokemonDetailRepositoryImpl called with correct parameter`() = runTest {
        whenever(repository.getPokemonDetail(any())).thenAnswer {
            flow<Resource<Any>> { emit(Resource.Loading()) }
        }

        viewModel.getPokemonDetail(name)
        verify(repository).getPokemonDetail(eq(name))
    }

    @Test
    fun `verify setLoading function called with isLoading=true `() = runTest {
        viewModel.setLoading(true)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(PokemonDetailViewState.Loading::class.java)
        val loadingState = currentState.value as PokemonDetailViewState.Loading
        assertThat(loadingState.isLoading).isEqualTo(true)
    }

    @Test
    fun `verify setLoading function called with isLoading=false `() = runTest {
        viewModel.setLoading(false)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(PokemonDetailViewState.Loading::class.java)
        val loadingState = currentState.value as PokemonDetailViewState.Loading
        assertThat(loadingState.isLoading).isEqualTo(false)
    }

    @After
    fun tearDown() = Dispatchers.resetMain()

}
