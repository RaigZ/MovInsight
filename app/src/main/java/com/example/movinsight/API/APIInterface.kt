package com.example.movinsight.API

import com.example.movinsight.DataAPIItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface APIInterface {
    @GET("posts")
    fun getAPIData(): Call<List<DataAPIItem>>

    @GET("api/v1/getWeekTop10")
    fun getTop10(
        @Header("X-RapidAPI-Key") key: String = com.example.movinsight.BuildConfig.RAPID_API_KEY
    ): Call<TopMovieResponse>

    @GET("api/v1/searchIMDB")
    fun searchIMDB(
        @Query("query") query: String,
        @Header("X-RapidAPI-Key") key: String = com.example.movinsight.BuildConfig.RAPID_API_KEY
    ): Call<SearchMovieResponse>
}