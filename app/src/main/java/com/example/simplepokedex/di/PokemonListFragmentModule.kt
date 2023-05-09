package com.example.simplepokedex.di

import com.example.simplepokedex.adapter.PokemonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object PokemonListFragmentModule {

    @Provides
    fun providePokemonAdapter() : PokemonAdapter {
        return PokemonAdapter()
    }
}