package com.example.movinsight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.movinsight.API.APIInterface
import com.example.movinsight.fragments.DisplayFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.movinsight.API.APIService
import com.example.movinsight.API.SearchMovieResponse
import com.example.movinsight.API.TopMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val retrofitBuilder by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(APIInterface::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Creating fragment instances
        val APIFragment = ApiFragment()
        val DisplayFragment = DisplayFragment()

        //Testing
        val imdbAPI = APIService.imdbAPI
        //top10(imdbAPI)
        searchMovie(imdbAPI)

        changeFragment(DisplayFragment)

        findViewById<BottomNavigationView>(R.id.bottom_nav).setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.ic_api -> {
                    changeFragment(APIFragment)
                    true
                }
                R.id.ic_display -> {
                    changeFragment(DisplayFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fContainer, fragment)
            commit()
        }
    }

    /*
    * An example request of imdbs search api, remove spaces in the movie/show name
    * this doesn't need to be in a function.
    * */
    private fun searchMovie(api: APIInterface) {
        val data = api.searchIMDB("Lordoftherings")
        data.enqueue(object: Callback<SearchMovieResponse> {
            override fun onResponse(
                call: Call<SearchMovieResponse>,
                response: Response<SearchMovieResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("ApiResponse", "${apiResponse}")
                } else {
                    // Log the error response
                    Log.e("ApiResponseError", response.errorBody()?.string() ?: "Unknown error")
                }
            }

            override fun onFailure(call: Call<SearchMovieResponse>, t: Throwable) {
                Log.d("APIFAIL", "message" +  t.message)
            }
        })

    }

    /*
    * An example request of imdbs top 10 movies,
    * this doesn't need to be in a function.
    * */
    private fun top10(api: APIInterface) {
        val data = api.getTop10()
        data.enqueue(object: Callback<TopMovieResponse> {
            override fun onResponse(call: Call<TopMovieResponse>, response: Response<TopMovieResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("ApiResponse", "${apiResponse}")
                } else {
                    // Log the error response
                    Log.e("ApiResponseError", response.errorBody()?.string() ?: "Unknown error")
                }
            }

            override fun onFailure(call: Call<TopMovieResponse>, t: Throwable) {
                Log.d("APIFAIL", "message" +  t.message)
            }
        })
    }

}