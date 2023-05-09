package com.example.simplepokedex.di

import com.example.simplepokedex.util.DispatcherProvider
import com.example.simplepokedex.util.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Singleton
    fun provideCoroutineDispatcherProvider() : DispatcherProvider {
        return DispatcherProviderImpl()
    }
}