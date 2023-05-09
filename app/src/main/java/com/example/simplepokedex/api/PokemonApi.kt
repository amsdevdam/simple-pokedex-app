package com.example.simplepokedex.api

import com.example.simplepokedex.model.PokemonListResponse
import com.example.simplepokedex.model.PokemonResponse
import com.example.simplepokedex.model.SpeciesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApi {
    companion object {
        const val POKEMON_PAGE_SIZE = 10
    }

    @GET("pokemon/")
    suspend fun getPokemon(@Query("limit") limit : Int = POKEMON_PAGE_SIZE, @Query("offset") offset : Int) : Response<PokemonListResponse>

    @GET
    suspend fun getSinglePokemon(@Url url : String) : Response<PokemonResponse>

    @GET
    suspend fun getSpecies(@Url url: String) : Response<SpeciesResponse>
}