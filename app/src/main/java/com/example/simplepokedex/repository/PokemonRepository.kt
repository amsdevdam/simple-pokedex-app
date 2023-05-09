package com.example.simplepokedex.repository

import com.example.simplepokedex.model.Pokemon

interface PokemonRepository {

    suspend fun getPokemon(pageNumber : Int) : List<Pokemon>?
}