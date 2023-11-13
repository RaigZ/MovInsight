package com.example.movinsight.API

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object APIService {
    private val BASE_URL = "https://imdb188.p.rapidapi.com/"

    val imdbAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(APIInterface::class.java)
    }
}