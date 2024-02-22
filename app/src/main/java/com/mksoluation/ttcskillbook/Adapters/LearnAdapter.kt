package com.mksoluation.ttcskillbook.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mksoluation.ttcskillbook.Models.Learn
import com.mksoluation.ttcskillbook.R

class LearnAdapter(val context: Context,val learnList: ArrayList<Learn>) : RecyclerView.Adapter<LearnAdapter.LearnHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.learn_item,parent,false)
       return LearnHolder(view)
    }

    override fun getItemCount(): Int {
        return learnList.size
    }

    override fun onBindViewHolder(holder: LearnHolder, position: Int) {
        val data  = learnList[position]
        holder.learnAns.text= data.description
        if (data.learnImageUrl.isNotEmpty()) {
            Glide.with(context)
                .load(data.learnImageUrl)
                .placeholder(R.drawable.plumbing)
                .into(holder.learnImage)
            holder.learnImage.visibility = View.VISIBLE
        } else {
            holder.learnImage.visibility = View.GONE // Hide the ImageView if no image URL
        }

        if (data.question.isNotEmpty()) {
            holder.learnQusetion.text = data.question
            holder.learnQusetion.visibility = View.VISIBLE
        } else {
            holder.learnQusetion.visibility = View.GONE // Hide the question TextView if empty
        }


    }
    inner class LearnHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val learnImage = itemView.findViewById<ImageView>(R.id.imageLearn)
        val learnAns = itemView.findViewById<TextView>(R.id.tvAns)
        val learnQusetion = itemView.findViewById<TextView>(R.id.tvQuestion)

    }
}