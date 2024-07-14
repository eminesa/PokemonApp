package com.eminesa.pokemonapp.screens.pokemondetail

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.eminesa.pokemonapp.databinding.FragmentPokemonDetailBinding
import com.eminesa.pokemonapp.extentions.showToastMessage
import com.eminesa.pokemonapp.model.PokemonDetail
import com.eminesa.pokemonapp.util.UiText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.random.Random

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {

    private var binding: FragmentPokemonDetailBinding? = null
    private val viewModel: PokemonDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (binding == null)
            binding = FragmentPokemonDetailBinding.inflate(inflater)

        binding?.buttonBack?.setOnClickListener {
            findNavController().popBackStack()
        }
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

            val gameIndices = pokemon.gameIndices

            // İlk oyun indeksinin rengine göre arka planı ayarlayalım
            val index = Random.nextInt(gameIndices?.size ?: 0)
            val versionName = gameIndices?.get(index)?.version?.name
            val color = viewModel.setColor(versionName)

            frameLayout.setBackgroundColor(color)

            typeLayout.setBackgroundColor(color)
            typeLayoutCard.setBackgroundColor(color)

            abilityLayoutCard.setBackgroundColor(color)

            val window = requireActivity().window
            window.statusBarColor = color

            for (type in pokemon.types!!) {
                val textView = TextView(requireContext())
                textView.text = type.type?.name
                textView.textSize = 18f
                textView.gravity = Gravity.CENTER_HORIZONTAL
                textView.setTextColor(Color.WHITE)
                textView.setPadding(16, 16, 16, 16)
                textView.setBackgroundColor(Color.TRANSPARENT)

                // Drawable ikonunu eklemek
                /*  val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back)
                  drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                  textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null) */

                typeLayout.addView(textView)
            }

            for (ability in pokemon.abilities!!) {
                val textView = TextView(requireContext())
                textView.text = ability.ability?.name
                textView.textSize = 18f
                textView.gravity = Gravity.CENTER_HORIZONTAL
                textView.setTextColor(Color.WHITE)
                textView.setPadding(16, 16, 16, 16)
                textView.setBackgroundColor(Color.TRANSPARENT)
                layoutAbility.addView(textView)
            }

            cardViewType.isVisible =true
            cardViewAbility.isVisible =true

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