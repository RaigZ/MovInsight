package com.example.movinsight.API

import com.example.movinsight.DataAPIItem
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("posts")
    fun getAPIData(): Call<List<DataAPIItem>>
}