package com.note.pokemonapp.screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.eminesa.pokemonapp.model.PokemonList
import com.eminesa.pokemonapp.network.PokemonRepositoryImpl
import com.eminesa.pokemonapp.screens.pokemons.PokemonsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class PokemonsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: PokemonsViewModel

    private lateinit var testScope: TestCoroutineScope

    @Mock
    private lateinit var observer: Observer<PagingData<PokemonList.Pokemon>>

    @Before
    fun setup() {
        testScope = TestCoroutineScope()
        viewModel = PokemonsViewModel(mock(PokemonRepositoryImpl::class.java))
    }

    @Test
    fun `given PokemonList available, when launchPagingAsync is called, then update pokemonList`() =
        testScope.runBlockingTest {
            // Given
            Dispatchers.setMain(TestCoroutineDispatcher())

            val pokemonList =
                MutableLiveData(PagingData.from(listOf(mock(PokemonList.Pokemon::class.java))))
            val hashMapPokemon =
                hashMapOf<String, MutableLiveData<PagingData<PokemonList.Pokemon>>?>(viewModel.pokemon to pokemonList)
            viewModel.hashMapPokemon = hashMapPokemon

            // When
            viewModel.launchPagingAsync()

            // Then
            viewModel.pokemonListWithLiveData?.observeForever(observer)
            pokemonList.value?.let { verify(observer).onChanged(it) }
            Dispatchers.resetMain()
        }

    @Test
    fun `given new PokemonList, when launchPagingAsync is called, then update pokemonList`() =
        testScope.runBlockingTest {
            // Given
            Dispatchers.setMain(TestCoroutineDispatcher())

            val pokemonList = MutableLiveData<PagingData<PokemonList.Pokemon>>()
            viewModel.hashMapPokemon[viewModel.pokemon] = pokemonList

            // When
            val mockPagingData = PagingData.from(listOf(mock(PokemonList.Pokemon::class.java)))
            pokemonList.postValue(mockPagingData)
            viewModel.launchPagingAsync()

            // Then
            viewModel.pokemonListWithLiveData?.observeForever(observer)
            verify(observer).onChanged(mockPagingData)

            Dispatchers.resetMain()
        }

    @After
    fun tearDown() {
        viewModel.pokemonListWithLiveData?.removeObserver(observer)
        testScope.cleanupTestCoroutines()
    }
}

