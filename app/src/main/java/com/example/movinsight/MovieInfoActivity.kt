package com.example.movinsight

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.movinsight.API.APIService
import com.example.movinsight.API.FirestoreService
import com.example.movinsight.API.OmdbMovieResponse
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieInfoActivity : AppCompatActivity() {

    private val userModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        //Go back to previous activity
        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish()
        }

        val intent = intent
        val title = intent.getStringExtra("title")

        val addToWatchlist: Button = findViewById(R.id.addMovie)
        val imageView: ImageView = findViewById(R.id.movieImage)
        val listView = findViewById<ListView>(R.id.movieList)

        if(title == null){
            Toast.makeText(this, "Unable to load movie details!", Toast.LENGTH_LONG)
            finish()
        }

        Log.d("MovieInfoAct", "${intent.getStringExtra("id")}")
        Log.d("MovieInfoAct", "${intent.getStringExtra("userId")}")
        Log.d("MovieInfoAct", "${intent.getStringExtra("title")}")

        addToWatchlist.setOnClickListener {
            if(FirestoreService.getUsername() != "")
            {
                FirestoreService.addToWatchlist(userModel, this, title.toString())
            }
            else
            {
                Toast.makeText(this, "Must be logged in to add to watchlist.", Toast.LENGTH_LONG).show()
            }
        }

        if (title != null) {
            searchOMDB(title, imageView, listView, this)
        }
    }
    private fun searchOMDB(name: String, imageView: ImageView, listView: ListView, context: Context) {
        val data = APIService.omdbAPI.searchOMDB(name)
        data.enqueue(object: Callback<OmdbMovieResponse> {
            override fun onResponse(call: Call<OmdbMovieResponse>, response: Response<OmdbMovieResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val labels = listOf("Title", "Year", "Rated", "Released", "Genre", "Director", "Writers", "Actors", "Plot", "Language", "imdbRating")
                    val movieData = listOf(
                        apiResponse?.Title,
                        apiResponse?.Year,
                        apiResponse?.Rated,
                        apiResponse?.Released,
                        apiResponse?.Genre,
                        apiResponse?.Director,
                        apiResponse?.Writers,
                        apiResponse?.Actors,
                        apiResponse?.Plot,
                        apiResponse?.Language,
                        apiResponse?.imdbRating
                    ).mapNotNull { it }

                    listView.adapter = MovieActivityAdapter(context, movieData, labels)

                    Log.d("TestOMDB", "${apiResponse}")

                    //Loading image
                    Picasso.get()
                        .load(apiResponse?.Poster)
                        .resize(620, 950)
                        .centerCrop()
                        .into(imageView)

                } else {
                    //Add toast
                    // Log the error response
                    Log.e("TestOMDBError", response.errorBody()?.string() ?: "Unknown error")
                    finish()
                }
            }

            override fun onFailure(call: Call<OmdbMovieResponse>, t: Throwable) {
                Log.d("TestOMDB_FAIL", "message" +  t.message)
            }

        })
    }

    private class MovieActivityAdapter(context: Context, mList: List<String>, labels: List<String>): BaseAdapter() {
        private val movieContext: Context
        private val movieList: List<String>
        private val movieLabels: List<String>

        init {
            movieContext = context
            movieList = mList
            movieLabels = labels
        }

        override fun getCount(): Int {
            return movieList.size
        }

        override fun getItem(position: Int): Any {
            return ""
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(movieContext)
            val row = layoutInflater.inflate(R.layout.movie_row, parent, false)
            val tvLabel = row.findViewById<TextView>(R.id.tvLabel)
            val tvMovieInfo = row.findViewById<TextView>(R.id.tvMovieInfo)

            tvLabel.text = movieLabels[position]
            tvMovieInfo.text = movieList[position]
            return row
        }

    }
}