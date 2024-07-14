package com.eminesa.pokemonapp.screens.pokemons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eminesa.pokemonapp.R
import com.eminesa.pokemonapp.adapter.PokemonAdapter
import com.eminesa.pokemonapp.adapter.PokemonLoadStateAdapter
import com.eminesa.pokemonapp.databinding.FragmentPokemonsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonsFragment : Fragment() {

    private var binding: FragmentPokemonsBinding? = null
    private var pokemonAdapter: PokemonAdapter? = null
    private val viewModel: PokemonsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (binding == null)
            binding = FragmentPokemonsBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniPokemonAdapter()
        binding?.initRecyclerView()
        getPokemon()
        initSwipeToRefresh()
        binding?.initClickListener()
    }

    private fun getPokemon() {
        viewModel.launchPagingAsync()
        lifecycleScope.launch {
            pokemonAdapter?.loadStateFlow?.collectLatest { loadStates ->
                binding?.swipeRefresh?.isRefreshing = loadStates.refresh is LoadState.Loading

                binding?.apply {
                    if (loadStates.refresh is LoadState.Error) {
                        swipeRefresh.visibility = View.GONE
                        incNetworkInfo.root.visibility = View.VISIBLE

                    } else {
                        swipeRefresh.visibility = View.VISIBLE
                        incNetworkInfo.root.visibility = View.GONE
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.pokemonListWithLiveData?.observe(viewLifecycleOwner) { pagingData ->
                pokemonAdapter?.submitData(lifecycle, pagingData)
            }
        }

        lifecycleScope.launch {
            pokemonAdapter?.loadStateFlow
                ?.distinctUntilChangedBy { it.refresh }
                ?.filter { it.refresh is LoadState.NotLoading }
                ?.collect {

                    if (viewModel.recyclerViewPosition == 0)
                        binding?.recyclerViewPokemon?.scrollToPosition(viewModel.recyclerViewPosition)

                }
        }

        pokemonAdapter?.addLoadStateListener { loadState ->

            if (loadState.append.endOfPaginationReached) {
                pokemonAdapter?.itemCount?.let { binding?.emptyPrivacyLayoutVisibility(it) }
            }
        }
    }

    private fun FragmentPokemonsBinding.emptyPrivacyLayoutVisibility(count: Int) {
        if (count < 1) {
            recyclerViewPokemon.visibility = View.GONE
            incEmpty.root.visibility = View.VISIBLE
        } else {
            recyclerViewPokemon.visibility = View.VISIBLE
            incEmpty.root.visibility = View.GONE
        }
    }

    private fun FragmentPokemonsBinding.initRecyclerView() {

        recyclerViewPokemon.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(0)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            /* val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
             dividerItemDecoration.setDrawable(
                 ColorDrawable(
                     ContextCompat.getColor(
                         context,
                         R.color.dark_gray
                     )
                 )
             )

             addItemDecoration(dividerItemDecoration) */
            adapter = pokemonAdapter?.withLoadStateHeaderAndFooter(
                header = PokemonLoadStateAdapter(pokemonAdapter),
                footer = PokemonLoadStateAdapter(pokemonAdapter)
            )
        }
    }

    private fun FragmentPokemonsBinding.initClickListener() {

        incNetworkInfo.txtNetworkRetry.setOnClickListener {
            recyclerViewPokemon.clearOnScrollListeners()
            pokemonAdapter?.retry()
        }
    }

    /**
     * Bu fonksiyon swipeRefresh'in listenerinı initial eder.
     */
    private fun initSwipeToRefresh() {

        binding?.swipeRefresh?.setOnRefreshListener {
            clearPokemonViewModelData()
        }
    }

    private fun clearPokemonViewModelData() {

        viewModel.apply {
            hashMapPokemon[pokemon] = null
            clearData()
            itemCount = 0
            isRefreshing = true
            pokemonId = ""
            recyclerViewPosition = 0
            getPokemon()
            isRefreshing = false
        }
    }

    private fun iniPokemonAdapter() {
        pokemonAdapter = PokemonAdapter(
            onItemViewListener = { id, item, position ->

                findNavController().navigate(
                    R.id.action_pokemonsFragment_to_pokemonDetailFragment,
                    bundleOf("id" to id )
                )
            })
    }

    override fun onPause() {
        // detail sayfasından geri geldginde en son kaldıgı yerden listeyi gosterir
        val layoutManager = binding?.recyclerViewPokemon?.layoutManager as StaggeredGridLayoutManager
        val lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null)
        val lastVisibleItemPosition = lastVisibleItemPositions.maxOrNull() ?: 0
        viewModel.recyclerViewPosition = lastVisibleItemPosition
        super.onPause()
    }

    override fun onDestroy() {

        findNavController().currentBackStackEntry?.viewModelStore?.clear()
        viewModelStore.clear()

        super.onDestroy()
    }

    override fun onDestroyView() {

        binding?.recyclerViewPokemon?.adapter = null

        viewModel.pokemonListWithLiveData?.removeObservers(viewLifecycleOwner)
        findNavController().currentBackStackEntry?.viewModelStore?.clear()
        binding?.swipeRefresh?.setOnRefreshListener(null)

        binding = null
        pokemonAdapter = null

        super.onDestroyView()
    }
}