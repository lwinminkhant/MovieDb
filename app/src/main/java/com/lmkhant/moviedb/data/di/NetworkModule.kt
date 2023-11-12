package com.lmkhant.moviedb.data.di

import android.content.Context
import com.lmkhant.moviedb.BuildConfig
import com.lmkhant.moviedb.data.network.ApiKeyInterceptor
import com.lmkhant.moviedb.data.network.MovieApi
import com.lmkhant.moviedb.data.network.NetworkConnectionInterceptor
import com.lmkhant.moviedb.utils.Utils

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideHttpClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(BuildConfig.API_KEY))
            .addInterceptor(NetworkConnectionInterceptor(context))
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
    fun provideNetworkApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)
}