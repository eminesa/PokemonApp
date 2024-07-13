package com.eminesa.pokemonapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eminesa.pokemonapp.databinding.ItemViewPokemonBinding
import com.eminesa.pokemonapp.model.PokemonList

class PokemonAdapter(
    val onItemViewListener: ((view: View, item: PokemonList.Pokemon, position: Int) -> Unit),
) : PagingDataAdapter<PokemonList.Pokemon, PokemonAdapter.PokemonViewHolder>(PokemonDiffUtil) {

    inner class PokemonViewHolder(private var itemBinding: ItemViewPokemonBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(pokemon: PokemonList.Pokemon) {

            itemBinding.apply {
                textViewPokemon.text = pokemon.name
            }
            itemView.setOnClickListener { onItemViewListener(it, pokemon, bindingAdapterPosition) }
        }
    }

    companion object PokemonDiffUtil :
        DiffUtil.ItemCallback<PokemonList.Pokemon>() {
        override fun areItemsTheSame(
            oldItem: PokemonList.Pokemon,
            newItem: PokemonList.Pokemon,
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: PokemonList.Pokemon,
            newItem: PokemonList.Pokemon,
        ): Boolean {
            return oldItem.url == newItem.url
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            ItemViewPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

}