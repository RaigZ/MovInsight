package com.example.movinsight.RecyclerView

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        holder.tvtitle.text = "TITLE: ${item.title.toString()}"
        holder.tvyear.text = "YEAR: ${item.year.toString()}"
        holder.tvstars.text = "STARS: ${item.stars.toString()}"
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
        val tvtitle: TextView = itemView.findViewById(R.id.tvtitle)
        val tvyear: TextView = itemView.findViewById(R.id.tvyear)
        val tvstars: TextView = itemView.findViewById(R.id.tvstars)

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
                intent.putExtra("title", item.title)
                /*
                intent.putExtra("id", item.id)
                intent.putExtra("qid", item.qid)
                intent.putExtra("year", item.year.toString())
                intent.putExtra("stars", item.stars)
                intent.putExtra("q", item.q)
                intent.putExtra("image", item.image)
                 */
                itemView.context.startActivity(intent)
            }
        }
    }
}