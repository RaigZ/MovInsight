package com.example.movinsight.RecyclerView

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.movinsight.DataAPIItem
import com.example.movinsight.R
import com.example.movinsight.RemoveActivity

class RVAdapter(private val dataList: List<DataAPIItem>) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.tvid.text = "ID: ${item.id.toString()}"
        holder.tvuserid.text = "UserID: ${item.id.toString()}"
        holder.tvtitle.text = "Title: ${item.title.toString()}"
        holder.tvcompleted.text = "Completed: ${item.completed.toString()}"
    }

    override fun getItemCount() : Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvid: TextView = itemView.findViewById(R.id.tvid)
        val tvuserid: TextView = itemView.findViewById(R.id.tvuserid)
        val tvtitle: TextView = itemView.findViewById(R.id.tvtitle)
        val tvcompleted: TextView = itemView.findViewById(R.id.tvcompleted)

        init {
            itemView.setOnClickListener {
                Log.d("In RVAdapter", "${dataList[adapterPosition].id}")
                Log.d("In RVAdapter", "${dataList[adapterPosition].userId}")
                Log.d("In RVAdapter", "${dataList[adapterPosition].title}")
                Log.d("In RVAdapter", "${dataList[adapterPosition].completed}")
                val intent = Intent(itemView.context, RemoveActivity::class.java)
                intent.putExtra("id", dataList[adapterPosition].id)
                intent.putExtra("userId", dataList[adapterPosition].userId)
                intent.putExtra("title", dataList[adapterPosition].title)
                intent.putExtra("completed", dataList[adapterPosition].completed)
                itemView.context.startActivity(intent)
                Toast.makeText(itemView.context, "You have clicked ${dataList[adapterPosition]}", Toast.LENGTH_LONG).show()
            }
        }
    }
}