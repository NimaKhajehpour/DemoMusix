package com.nima.demomusix.di

import com.nima.demomusix.network.DeezerApi
import com.nima.demomusix.repository.DeezerRepository
import com.nima.demomusix.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Provides
    @Singleton
    fun provideDeezerApi(): DeezerApi = Retrofit.Builder().baseUrl(Constants.baseUrl).addConverterFactory(
        GsonConverterFactory.create()
    ).build().create(DeezerApi::class.java)

    @Provides
    @Singleton
    fun provideDeezerRepository(api: DeezerApi) = DeezerRepository(api)
}