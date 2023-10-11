package com.lmkhant.moviedb.data.di

import com.lmkhant.moviedb.BuildConfig
import com.lmkhant.moviedb.data.network.ApiKeyInterceptor
import com.lmkhant.moviedb.data.network.NetworkApi
import com.lmkhant.moviedb.utils.Utils

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(BuildConfig.API_KEY))
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(Utils.BASE_URL)
        .build()

    @Singleton
    @Provides
    fun provideNetworkApi(retrofit: Retrofit): NetworkApi = retrofit.create(NetworkApi::class.java)
}