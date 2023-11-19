package com.example.movinsight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.movinsight.API.APIInterface
import com.example.movinsight.fragments.DisplayFragment
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.movinsight.API.APIService
import com.example.movinsight.API.SearchMovieResponse
import com.example.movinsight.API.TopMovieResponse
import com.example.movinsight.Room.User
import com.example.movinsight.Room.UserDatabase
import com.example.movinsight.fragments.LoginFragment
import com.example.movinsight.fragments.SignupFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val viewModel: MovInsightViewModel by viewModels()
    private val userModel: UserViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private val retrofitBuilder by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(APIInterface::class.java)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ROOM TEST - OPEN DB
        val db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java, "user"
        ).build()

        //If user is still logged in, display email(*username later),
        //and change visibility for login/signup/sign-out buttons
        auth = Firebase.auth
        val currentUser = auth.currentUser

        //When room is implemented check if currentUser == user in room.
        if(currentUser != null){
            findViewById<Button>(R.id.loginButton).visibility = View.GONE
            findViewById<Button>(R.id.signupButton).visibility = View.GONE
            findViewById<Button>(R.id.signoutButton).visibility = View.VISIBLE
            var usernameField = findViewById<TextView>(R.id.usernameField) //.visibility = View.VISIBLE
            usernameField.visibility = View.VISIBLE
            usernameField.text = auth.currentUser!!.email.toString()
        }

        //Creating fragment instances
        val APIFragment = ApiFragment()
        val DisplayFragment = DisplayFragment()
        val SignupFragment = SignupFragment()
        val LoginFragment = LoginFragment ()

        //UserViewModel, waits for a Map<String, Any> map to be returned
        userModel.selectedItem.observe(this, Observer { item ->
            var usernameField = findViewById<TextView>(R.id.usernameField)
            usernameField.visibility = View.VISIBLE
            usernameField.text = item["username"].toString()

            //Change visibility for login/sign-up/sign-out buttons
            findViewById<Button>(R.id.loginButton).visibility = View.GONE
            findViewById<Button>(R.id.signupButton).visibility = View.GONE
            findViewById<Button>(R.id.signoutButton).visibility = View.VISIBLE

            //Change this to MainFragment
            changeFragment(DisplayFragment)

            Log.d("UserViewModel", "$item")
        })

        //API service for IMDB, was testing top10, and search movies
        val imdbAPI = APIService.imdbAPI
        //top10(imdbAPI)
        //searchMovie(imdbAPI)

        changeFragment(DisplayFragment)

        findViewById<Button>(R.id.signupButton).setOnClickListener {
            changeFragment(SignupFragment)
        }

        findViewById<Button>(R.id.loginButton).setOnClickListener {
            changeFragment(LoginFragment)
        }

        //Signout button, if user clicks on signout, revert visibility for signup/login buttons
        //Set visibility for signout button to be GONE
        var signoutButton = findViewById<Button>(R.id.signoutButton)
        var usernameField = findViewById<TextView>(R.id.usernameField)

        //if(signoutButton.visibility == View.VISIBLE){}
        signoutButton.setOnClickListener {
            if(signoutButton.visibility == View.VISIBLE){
                //Log out of firebase auth provider
                auth.signOut()

                //Change visibility for login/sign-up/sign-out buttons
                findViewById<Button>(R.id.signupButton).visibility = View.VISIBLE
                findViewById<Button>(R.id.loginButton).visibility = View.VISIBLE
                usernameField.visibility = View.GONE
                signoutButton.visibility = View.GONE
                usernameField.text = ""
            }
        }

        //Used for the bottom nav that will be implemented
        /*findViewById<BottomNavigationView>(R.id.bottom_nav).setOnItemSelectedListener { item ->
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
        }*/


        // ROOM TEST - CLICK TO ADD USER TO DB
        findViewById<Button>(R.id.bAddToRoom).setOnClickListener {
            lifecycleScope.launch { // coroutine on Main
                insertUserToRoomDB(db)
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

    // Note to self: The database only opens when a change is made
    // Testing the Room database
    private suspend fun insertUserToRoomDB(db : UserDatabase) {
        val userDao = db.userDao()

        var watchlist = ArrayList<String>()
        watchlist.add("movie1")
        watchlist.add("movie2")
        watchlist.add("movie3")
        val user1 = User("guyDudeBro", "dopeEmail", watchlist)
        userDao.insertUser(user1)
    }
}