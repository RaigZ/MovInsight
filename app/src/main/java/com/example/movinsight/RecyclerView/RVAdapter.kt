package com.example.movinsight.RecyclerView

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.movinsight.API.SearchMovieResponse
import com.example.movinsight.API.searchItem
import com.example.movinsight.R
import com.example.movinsight.MovieInfoActivity

class RVAdapter(private var dataList: List<searchItem>) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_design, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.tvid.text = "ID: ${item.id.toString()}"
        // Is the line below need to be displayed?
        //holder.tvqid.text = "QID: ${item.id.toString()}"
        holder.tvtitle.text = "TITLE: ${item.title.toString()}"
        holder.tvyear.text = "YEAR: ${item.year.toString()}"
        holder.tvstars.text = "STARS: ${item.stars.toString()}"
        holder.tvq.text = "Q: ${item.q.toString()}"
       // holder.imageURL.text = "IMAGE URL: ${item.image.toString()}"

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newSearchItems: SearchMovieResponse) {
        dataList = newSearchItems.data
        notifyDataSetChanged()
    }

    override fun getItemCount() : Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvid: TextView = itemView.findViewById(R.id.tvid)
        //val tvqid: TextView = itemView.findViewById(R.id.tvqid)
        val tvtitle: TextView = itemView.findViewById(R.id.tvtitle)
        val tvyear: TextView = itemView.findViewById(R.id.tvyear)
        val tvstars: TextView = itemView.findViewById(R.id.tvstars)
        val tvq: TextView = itemView.findViewById(R.id.tvq)
        //val imageURL: TextView = itemView.findViewById(R.id.imageURL)

        init {
            itemView.setOnClickListener {
                val item = dataList[adapterPosition]
                Log.d("In RVAdapter", item.id)
                Log.d("In RVAdapter", item.qid)
                Log.d("In RVAdapter", item.title)
                Log.d("In RVAdapter", item.year.toString())
                Log.d("In RVAdapter", item.stars)
                Log.d("In RVAdapter", item.q)
                Log.d("In RVAdapter", item.image)

                val intent = Intent(itemView.context, MovieInfoActivity::class.java)
                intent.putExtra("id", item.id)
                intent.putExtra("qid", item.qid)
                intent.putExtra("title", item.title)
                intent.putExtra("year", item.year.toString())
                intent.putExtra("stars", item.stars)
                intent.putExtra("q", item.q)
                intent.putExtra("image", item.image)
                itemView.context.startActivity(intent)
                Toast.makeText(itemView.context, "You have clicked $item", Toast.LENGTH_LONG).show()
            }
        }
    }
}