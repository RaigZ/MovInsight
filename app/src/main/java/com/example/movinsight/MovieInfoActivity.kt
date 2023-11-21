package com.example.movinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.movinsight.API.FirestoreService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class MovieInfoActivity : AppCompatActivity() {
    private var db = FirestoreService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        findViewById<Button>(R.id.backButton).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        val intent = intent
        val addToWatchlist: Button = findViewById(R.id.addMovie)

        val id = intent.getStringExtra("id")
        val userId = intent.getStringExtra("userId")
        val title = intent.getStringExtra("title")
        val imageView: ImageView = findViewById(R.id.movieImage)
        findViewById<TextView>(R.id.showId).text = "ID: " + id
        findViewById<TextView>(R.id.showUserId).text = "UserID: " + userId
        findViewById<TextView>(R.id.showTitle).text = "Title: " + title

        Log.d("RemoteActivity", "${intent.getStringExtra("id")}")
        Log.d("RemoteActivity", "${intent.getStringExtra("userId")}")
        Log.d("RemoteActivity", "${intent.getStringExtra("title")}")

        Picasso.get()
            .load(intent.getStringExtra("image"))
            .resize(620, 950)
            .centerCrop()
            .into(imageView)

        addToWatchlist.setOnClickListener {
            FirestoreService.addToWatchlist(title.toString())
        }
    }
}