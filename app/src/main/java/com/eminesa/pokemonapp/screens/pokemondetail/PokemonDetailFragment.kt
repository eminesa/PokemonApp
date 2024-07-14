package com.eminesa.pokemonapp.screens.pokemondetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.util.CoilUtils
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.eminesa.pokemonapp.R
import com.eminesa.pokemonapp.databinding.FragmentPokemonDetailBinding
import com.eminesa.pokemonapp.extentions.showToastMessage
import com.eminesa.pokemonapp.model.PokemonDetail
import com.eminesa.pokemonapp.util.UiText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {

    var binding: FragmentPokemonDetailBinding? = null
    private val viewModel: PokemonDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (binding == null)
            binding = FragmentPokemonDetailBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        viewModel.getPokemonDetail(arguments?.getString("id", "1"))

    }

    private fun setupObservers() {
        viewModel.getViewState().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }.launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: PokemonDetailViewState) {
        when (state) {
            is PokemonDetailViewState.Init -> Unit
            is PokemonDetailViewState.Loading -> handleLoading(state.isLoading)
            is PokemonDetailViewState.Success -> handleSuccess(state.data)
            is PokemonDetailViewState.SuccessWithEmptyData -> Unit
            is PokemonDetailViewState.Error -> handleError(state.error)
            else -> {

            }
        }
    }

    private fun handleLoading(loading: Boolean) {
        binding?.apply {
            progressBar.isVisible = loading
        }
    }

    private fun handleSuccess(pokemon: PokemonDetail) {

        val imageUrl = pokemon.sprites?.other?.dreamWorld?.frontDefault ?: ""

        binding?.apply {

            val imageLoader = ImageLoader.Builder(requireContext())
                .components {
                    add(SvgDecoder.Factory())
                }
                .build()

            val request = ImageRequest.Builder(requireContext())
                .data(imageUrl)
                .target(imageViewPokemon)
                .build()

            imageLoader.enqueue(request)
            textViewPokemonName.text = pokemon.name
        }

    }

    private fun handleError(error: UiText) =
        error.asString(requireContext()).showToastMessage(requireContext())

    override fun onDestroy() {

        findNavController().currentBackStackEntry?.viewModelStore?.clear()
        viewModelStore.clear()

        super.onDestroy()
    }

    override fun onDestroyView() {

        findNavController().currentBackStackEntry?.viewModelStore?.clear()

        binding = null

        super.onDestroyView()
    }
}