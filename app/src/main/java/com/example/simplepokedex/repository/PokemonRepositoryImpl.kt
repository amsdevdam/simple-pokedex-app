package com.example.simplepokedex.repository

import android.graphics.Bitmap
import android.util.Log
import com.example.simplepokedex.api.PokemonApi
import com.example.simplepokedex.model.Pokemon
import com.example.simplepokedex.model.PokemonResponse
import com.example.simplepokedex.network.RetrofitInstance
import com.example.simplepokedex.util.ImageLoader
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val retrofitInstance: RetrofitInstance,
    private val imageLoader : ImageLoader
) : PokemonRepository {

    override suspend fun getPokemon(pageNumber : Int): List<Pokemon>? = getResults(pageNumber)

    private suspend fun getResults(pageNumber : Int): List<Pokemon>? {
        val response = try {
            retrofitInstance.pokemonApi.getPokemon(offset = pageNumber * PokemonApi.POKEMON_PAGE_SIZE)
        } catch (e: Exception) {
            when (e) {
                is IOException -> Log.e(
                    "PokemonRepositoryImpl",
                    "IOException: Check your internet connection"
                )
                is HttpException -> Log.e(
                    "PokemonRepositoryImpl",
                    "HttpException: Unexpected response"
                )
                else -> Log.e("PokemonRepositoryImpl", e.message ?: "Unknown error")
            }
            return null
        }

        val pokemonList = mutableListOf<Pokemon>()
        if (response.isSuccessful && response.body() != null) {
            response.body()!!.results.forEach { pokemonListItem ->
                val pokemon = getSinglePokemon(pokemonListItem.url)
                pokemon?.let {
                    pokemonList.add(it)
                }
            }
        } else {
            Log.e("PokemonRepositoryImpl", "Response not successful")
            return null
        }

        return pokemonList
    }

    private suspend fun getSinglePokemon(url: String): Pokemon? {
        val pokemonResponse = try {
            retrofitInstance.pokemonApi.getSinglePokemon(url)
        } catch (e: Exception) {
            when (e) {
                is IOException -> Log.e(
                    "PokemonRepositoryImpl",
                    "IOException: Check your internet connection"
                )
                is HttpException -> Log.e(
                    "PokemonRepositoryImpl",
                    "HttpException: Unexpected response"
                )
                else -> Log.e("PokemonRepositoryImpl", e.message ?: "Unknown error")
            }
            return null
        }

        return if (pokemonResponse.isSuccessful && pokemonResponse.body() != null) {
            val responseBody = pokemonResponse.body()!!
            val pokemonImage =
                getPokemonImage(responseBody.sprites.other.officialArtwork.front_default)
            val pokemonTypes = getPokemonTypes(responseBody)
            val pokemonDescription = getPokemonDescription(responseBody)

            Pokemon(pokemonResponse.body()!!.name, pokemonTypes, pokemonDescription ?: "ERROR", pokemonImage)
        } else {
            Log.e("PokemonRepositoryImpl", "Response not successful")
            null
        }
    }

    private fun getPokemonTypes(body: PokemonResponse): List<String> {
        val typesStrList = mutableListOf<String>()
        body.types.forEach {
            typesStrList.add(it.type.name)
        }

        return typesStrList
    }

    private suspend fun getPokemonDescription(body: PokemonResponse): String? {
        val speciesResponse = try {
            retrofitInstance.pokemonApi.getSpecies(body.species.url)
        } catch (e: Exception) {
            when (e) {
                is IOException -> Log.e(
                    "PokemonRepositoryImpl",
                    "IOException: Check your internet connection"
                )
                is HttpException -> Log.e(
                    "PokemonRepositoryImpl",
                    "HttpException: Unexpected response"
                )
                else -> Log.e("PokemonRepositoryImpl", e.message ?: "Unknown error")
            }
            return null
        }

        return if (speciesResponse.isSuccessful && speciesResponse.body() != null) {
            speciesResponse.body()!!.flavor_text_entries[0].flavor_text
        } else {
            Log.e("PokemonRepositoryImpl", "Response not successful")
            null
        }
    }

    private fun getPokemonImage(url: String): Bitmap {
        return imageLoader.getImage(url)
    }
}