package com.example.simplepokedex.network

import com.example.simplepokedex.api.PokemonApi
import javax.inject.Inject

class RetrofitInstance @Inject constructor(
    val pokemonApi: PokemonApi
)