package com.mksoluation.ttcskillbook.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mksoluation.ttcskillbook.Models.Notification
import com.mksoluation.ttcskillbook.R

class NotificationAdapter (val context: Context, val notificationList: ArrayList<Notification>) : RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.learn_item,parent,false)
        return NotificationHolder(view)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        val data  = notificationList[position]
        holder.NotifiAns.text= data.description
        if (data.NotificationImageUrl.isNotEmpty()) {
            Glide.with(context)
                .load(data.NotificationImageUrl)
                .placeholder(R.drawable.plumbing)
                .into(holder.NotifiImage)
            holder.NotifiImage.visibility = View.VISIBLE
        } else {
            holder.NotifiImage.visibility = View.GONE // Hide the ImageView if no image URL
        }

        if (data.title.isNotEmpty()) {
            holder.NotifiTitle.text = data.title
            holder.NotifiTitle.visibility = View.VISIBLE
        } else {
            holder.NotifiTitle.visibility = View.GONE // Hide the question TextView if empty
        }


    }
    inner class NotificationHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val NotifiImage = itemView.findViewById<ImageView>(R.id.imageLearn)
        val NotifiAns = itemView.findViewById<TextView>(R.id.tvAns)
        val NotifiTitle = itemView.findViewById<TextView>(R.id.tvQuestion)

    }
}