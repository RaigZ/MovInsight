package com.example.movinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WatchlistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchlist)

        findViewById<Button>(R.id.back_button).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        //get_watchlist()
    }

    //API watchlist -- where to get movies and display to recyclerview
    //old code. going to go base off this
    /*private fun get_watchlist() {
        val myList = mutableListOf<DataAPIItem>()
        var count = 0
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnCompleteListener {
                val result: StringBuffer = StringBuffer()
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        val complete = "${document.data.getValue("completed")}".toBoolean()
                        val id = "${document.data.getValue("id")}".toInt()
                        val title = "${document.data.getValue("title")}"
                        val userId = "${document.data.getValue("userId")}".toInt()
                        val item = DataAPIItem(complete, id, title, userId)
                        myList.add(item)
                        count++
                        /*val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
                        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        recyclerView.adapter = RVAdapter(myList)*/
                    }
                    val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
                    recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                    recyclerView.adapter = RVAdapter(myList)
                }
            }
    }*/
}