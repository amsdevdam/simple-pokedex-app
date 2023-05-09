package com.example.simplepokedex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.simplepokedex.databinding.FragmentPokemonDetailsBinding
import com.example.simplepokedex.model.Pokemon

class PokemonDetailsFragment : Fragment() {

    private lateinit var _binding : FragmentPokemonDetailsBinding
    private val binding get() = _binding
    private val args : PokemonDetailsFragmentArgs by navArgs()
    private lateinit var pokemon : Pokemon

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)

        pokemon = args.pokemon
        setupViews()

        return binding.root
    }

    private fun setupViews() {
        binding.pokemonDetailsNameTextView.text = pokemon.name.replaceFirstChar { it.uppercaseChar() }
        binding.pokemonDetailsImageView.setImageBitmap(pokemon.image)
        var typesStr = "Type: "
        pokemon.types.forEach {
            typesStr += "${it.replaceFirstChar { ch -> ch.uppercaseChar() }}, "
        }

        typesStr = typesStr.dropLast(2)
        binding.pokemonDetailsTypeTextView.text = typesStr
        binding.pokemonDetailsDescriptionTextView.text = pokemon.description.replace("\n", " ")
    }
}