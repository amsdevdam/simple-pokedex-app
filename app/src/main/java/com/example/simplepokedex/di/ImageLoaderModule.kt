package com.example.simplepokedex.di

import android.content.Context
import com.example.simplepokedex.util.GlideImageLoader
import com.example.simplepokedex.util.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context) : ImageLoader {
        return GlideImageLoader(context)
    }
}