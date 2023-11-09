package com.example.movinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RemoveActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove)
        findViewById<Button>(R.id.backButton).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        db = Firebase.firestore
        val intent = intent

        val id = intent.getIntExtra("id", 0)
        val userId = intent.getIntExtra("userId", 0)
        val title = intent.getStringExtra("title")
        findViewById<TextView>(R.id.showId).text = "ID: " + id.toString()
        findViewById<TextView>(R.id.showUserId).text = "UserID: " + userId.toString()
        findViewById<TextView>(R.id.showTitle).text = "Title: " + title.toString()
        Log.d("RemoteActivity", "${intent.getIntExtra("id",  0)}")
        Log.d("RemoteActivity", "${intent.getIntExtra("userId",  0)}")
        Log.d("RemoteActivity", "${intent.getStringExtra("title")}")
        Log.d("RemoteActivity", "${intent.getBooleanExtra("completed", false)}")

        //Deriving document that matches
        findViewById<Button>(R.id.deleteData).setOnClickListener{
            var query = db.collection("placeholder")
                .whereEqualTo("id", id)
                .whereEqualTo("userId", userId)
                .whereEqualTo("title", title)
            //Delete document derived
            query.get()
                .addOnSuccessListener { snapshot ->
                    for(document in snapshot){
                        document.reference.delete()
                        Toast.makeText(this, "Deleted data", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error deleting data", Toast.LENGTH_LONG).show()
                }
        }
    }
}