package com.example.movinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.movinsight.API.FirestoreService

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        
        Log.d("ProfileActivity", FirestoreService.getUserEmail())

        findViewById<Button>(R.id.back_button).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}