package com.mksoluation.ttcskillbook.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mksoluation.ttcskillbook.Models.Signal
import com.mksoluation.ttcskillbook.R

class SignalAdapter (var context: Context, var signalList:ArrayList<Signal>) : RecyclerView.Adapter<SignalAdapter.SignalHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignalHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.signal_item, parent, false)
        return SignalHolder(view)
    }
    override fun getItemCount(): Int {
        return signalList.size
    }

    override fun onBindViewHolder(holder: SignalHolder, position: Int) {
        val data = signalList[position]
        holder.signalAbout.text = data.description
        if (data.SignalImageUrl.isNotEmpty()) {
            Glide.with(context).load(data.SignalImageUrl).placeholder(R.drawable.elactrical1)
                .into(holder.signalImage)
        }
    }
    inner class SignalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val signalImage = itemView.findViewById<ImageView>(R.id.imgSignal)
        val signalAbout = itemView.findViewById<TextView>(R.id.tvAbout)

    }
}