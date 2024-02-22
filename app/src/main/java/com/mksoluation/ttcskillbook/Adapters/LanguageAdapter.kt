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

class LanguageAdapter (val context: Context, val learnLanguaList: ArrayList<Learn>) : RecyclerView.Adapter<LanguageAdapter.LanguageHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.language,parent,false)
        return LanguageHolder(view)
    }

    override fun getItemCount(): Int {
        return learnLanguaList.size
    }

    override fun onBindViewHolder(holder: LanguageHolder, position: Int) {
        val data  = learnLanguaList[position]
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
    inner class LanguageHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val learnImage = itemView.findViewById<ImageView>(R.id.imageLearnll)
        val learnAns = itemView.findViewById<TextView>(R.id.tvAnsll)
        val learnQusetion = itemView.findViewById<TextView>(R.id.tvQuestionll)

    }

}