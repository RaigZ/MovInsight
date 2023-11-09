package com.example.movinsight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.movinsight.API.APIInterface
import com.example.movinsight.fragments.DisplayFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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

}