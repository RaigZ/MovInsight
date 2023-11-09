package com.example.movinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        findViewById<Button>(R.id.backButton).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        db = Firebase.firestore

        findViewById<Button>(R.id.apiAdd).setOnClickListener{
            //Derive details
            val userId = findViewById<EditText>(R.id.enterUserID).text.toString().toInt()
            val id = findViewById<EditText>(R.id.enterID).text.toString().toInt()
            val title = findViewById<EditText>(R.id.enterTitle).text.toString()

            val data = buildMap<String, Any> {
                put("userId", userId)
                put("id", id)
                put("title", title)
                put("completed", false)
            }
            //Add details to firestore db
            db.collection("placeholder")
                .add(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Adding data success!", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Adding data fail!", Toast.LENGTH_LONG).show()
                }
        }
    }
}