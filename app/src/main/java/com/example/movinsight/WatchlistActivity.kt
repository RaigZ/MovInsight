package com.example.movinsight

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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

            fun createNotificationChannel() {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel: NotificationChannel = NotificationChannel("C11", "watchlistDeletion", NotificationManager.IMPORTANCE_DEFAULT).apply {
                        var description = "Description"
                    }
                    val notificationManager: NotificationManager = itemView.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                }
            }

            fun showDeleteAlert(title: String) {
                AlertDialog.Builder(itemView.context)
                    .setTitle("Deleted watchlist item")
                    .setMessage("The movie title \"$title\" has been deleted from your watchlist.")
                    .setPositiveButton("Okay"){_,_ ->}
                    .show()
            }

            //noinspection MissingPermission
            fun sendDeleteNotification() {
                showDeleteAlert(movieName.text.toString())
                val builder: NotificationCompat.Builder = NotificationCompat.Builder(itemView.context, "C11")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(movieName.text.toString())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                with(NotificationManagerCompat.from(itemView.context)) {
                    notify(11, builder.build())
                }
            }

            init {
                itemView.setOnClickListener {
                    val item = watchlist[adapterPosition]
                    val intent = Intent(itemView.context, MovieInfoActivity::class.java)
                    intent.putExtra("title", item)
                    itemView.context.startActivity(intent)
                }

                createNotificationChannel()
                itemView.findViewById<Button>(R.id.deleteMovie).setOnClickListener{
                    val item = watchlist[adapterPosition]
                    watchlist.removeAt(adapterPosition)
                    Log.d("WatchlistRecycler", "$watchlist")
                    FirestoreService.setWatchlist(watchlist)
                    FirestoreService.updateWatchlist() {updateSuccessful ->
                        if(updateSuccessful){
                            notifyDataSetChanged()
                            sendDeleteNotification()
                        } else {
                            Toast.makeText(itemView.context, "Error deleting movie", Toast.LENGTH_LONG)
                        }
                    }
                }
            }
        }
    } 
}