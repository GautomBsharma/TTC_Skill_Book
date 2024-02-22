package com.mksoluation.ttcskillbook.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mksoluation.ttcskillbook.Models.Tool
import com.mksoluation.ttcskillbook.R

class ToolAdapter (val context: Context,val toolList: ArrayList<Tool>):RecyclerView.Adapter<ToolAdapter.ToolHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.tool_item,parent,false)
        return ToolHolder(view)
    }

    override fun getItemCount(): Int {
       return toolList.size
    }

    override fun onBindViewHolder(holder: ToolHolder, position: Int) {
        val data = toolList[position]
        holder.toolAbout.text= data.description
        if (data.toolImageUrl.isNotEmpty()) {
            Glide.with(context).load(data.toolImageUrl).placeholder(R.drawable.plumbing).into(holder.toolImage)
        }
    }
    inner class ToolHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val toolImage = itemView.findViewById<ImageView>(R.id.imageTool)
        val toolAbout = itemView.findViewById<TextView>(R.id.tvtooldes)
    }
}