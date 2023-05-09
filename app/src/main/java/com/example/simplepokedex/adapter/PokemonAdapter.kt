package com.example.simplepokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplepokedex.R
import com.example.simplepokedex.model.Pokemon

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    fun interface OnItemClickListener {
        fun onItemClick(pokemon: Pokemon)
    }

    private lateinit var onItemClickListener: OnItemClickListener
    private var pokemon = mutableListOf<Pokemon>()

    inner class ViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val pokemonImageIv: ImageView = itemView.findViewById(R.id.pokemon_image_view)
        val pokemonNameTv: TextView = itemView.findViewById(R.id.pokemon_name_text_view)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(pokemon[adapterPosition])
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_card_view, parent, false),
            onItemClickListener
        )
    }

    override fun getItemCount(): Int {
        return pokemon.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemon[position]
        holder.pokemonImageIv.setImageBitmap(pokemon.image)
        holder.pokemonNameTv.text = pokemon.name.replaceFirstChar {it.uppercaseChar()}
    }

    fun appendDataSet(it: List<Pokemon>) {
        pokemon.addAll(it)
        notifyDataSetChanged()
    }
}