package com.lmkhant.moviedb

import com.lmkhant.moviedb.data.network.MovieApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun testApiInstance(baseUrl: String): MovieApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(MovieApi::class.java)
    }
}



