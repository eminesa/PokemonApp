package com.eminesa.pokemonapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eminesa.pokemonapp.databinding.ItemNetworkStateBinding
import com.eminesa.pokemonapp.extentions.handleError
import com.eminesa.pokemonapp.extentions.showNetworkError
import com.eminesa.pokemonapp.util.getNetworkErrorMessage

class PokemonLoadStateAdapter(private val adapter: PokemonAdapter?) :
    LoadStateAdapter<PokemonLoadStateAdapter.NetworkStateItemViewHolder>() {

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): NetworkStateItemViewHolder {
        return NetworkStateItemViewHolder(ItemNetworkStateBinding.inflate(LayoutInflater.from(parent.context))) { adapter?.retry() }
    }

    inner class NetworkStateItemViewHolder(
        binding: ItemNetworkStateBinding,
        private val retryCallback: () -> Unit,
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {

        private val progressBar = binding.progressBar
        private val rootView = binding.viewSize
        private val retry = binding.retryButton
            .also {
                it.setOnClickListener { retryCallback() }
            }

        fun bindTo(loadState: LoadState) {

            val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,  // genişlik
                ConstraintLayout.LayoutParams.WRAP_CONTENT // yükseklik
            )
            rootView.layoutParams = params

            progressBar.isVisible = loadState is LoadState.Loading
            retry.isVisible = loadState is LoadState.Error

            (loadState as? LoadState.Error)?.error?.let {
                getNetworkErrorMessage(it).showNetworkError(progressBar.context)
            }

        }
    }
}