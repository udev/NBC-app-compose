package com.nbc.uitest.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ShowModule {
    @Provides
    fun provideShowRepository(
        gson: Gson
    ): ShowService =
        ShowRepository(gson)

    @Provides
    fun provideGson(): Gson = Gson()
}