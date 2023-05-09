package com.example.simplepokedex.di

import com.example.simplepokedex.network.RetrofitInstance
import com.example.simplepokedex.repository.PokemonRepository
import com.example.simplepokedex.repository.PokemonRepositoryImpl
import com.example.simplepokedex.util.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(retrofitInstance: RetrofitInstance, imageLoader: ImageLoader) : PokemonRepository {
        return PokemonRepositoryImpl(retrofitInstance, imageLoader)
    }
}