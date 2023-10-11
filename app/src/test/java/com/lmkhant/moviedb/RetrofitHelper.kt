package com.lmkhant.moviedb

import com.lmkhant.moviedb.data.network.NetworkApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun testApiInstance(baseUrl: String): NetworkApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NetworkApi::class.java)
    }
}



