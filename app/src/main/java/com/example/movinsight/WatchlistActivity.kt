package com.example.movinsight

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movinsight.API.FirestoreService
import org.w3c.dom.Text

class WatchlistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchlist)
        val username = findViewById<TextView>(R.id.tvWatchlist)
        username.text = FirestoreService.getUsername() + " watchlist"

        val recyclerView: RecyclerView = findViewById(R.id.watchlistRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val customAdapter = WatchlistActivityAdapter()

        findViewById<Button>(R.id.back_button).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        Log.d("WatchlistActivity", "${FirestoreService.getWatchlist()}")
        recyclerView.adapter = customAdapter
    }

    private class WatchlistActivityAdapter(): RecyclerView.Adapter<WatchlistActivityAdapter.ViewHolder>() {
        private val watchlist: MutableList<String> = FirestoreService.getWatchlist()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): WatchlistActivityAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.watchlist_row, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: WatchlistActivityAdapter.ViewHolder, position: Int) {
            val item = watchlist[position]
            holder.movieName.text = item
        }

        override fun getItemCount(): Int {
            return watchlist.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val movieName: TextView = itemView.findViewById(R.id.movieName)
            val deleteMovie: Button = itemView.findViewById(R.id.deleteMovie)

            init {
                itemView.setOnClickListener {
                    val item = watchlist[adapterPosition]
                    val intent = Intent(itemView.context, MovieInfoActivity::class.java)
                    intent.putExtra("title", item)
                    itemView.context.startActivity(intent)
                }
                itemView.findViewById<Button>(R.id.deleteMovie).setOnClickListener{
                    val item = watchlist[adapterPosition]
                    watchlist.removeAt(adapterPosition)
                    Log.d("WatchlistRecycler", "$watchlist")
                    FirestoreService.setWatchlist(watchlist)
                    FirestoreService.updateWatchlist() {updateSuccessful ->
                        if(updateSuccessful){
                            notifyDataSetChanged()
                        } else {
                            Toast.makeText(itemView.context, "Error deleting movie", Toast.LENGTH_LONG)
                        }
                    }
                }
            }
        }
    } 
}