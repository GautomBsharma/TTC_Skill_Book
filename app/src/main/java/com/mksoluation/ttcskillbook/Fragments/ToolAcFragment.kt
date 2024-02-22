package com.mksoluation.ttcskillbook.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mksoluation.ttcskillbook.Adapters.ToolAdapter
import com.mksoluation.ttcskillbook.AddToolActivity
import com.mksoluation.ttcskillbook.Models.Admin
import com.mksoluation.ttcskillbook.Models.Tool
import com.mksoluation.ttcskillbook.R
import com.mksoluation.ttcskillbook.databinding.FragmentToolAcBinding


class ToolAcFragment : Fragment() {
    private lateinit var binding:FragmentToolAcBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: ToolAdapter
    private var toolList:ArrayList<Tool>?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentToolAcBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        toolList = ArrayList()
        binding.recyclerToolac.layoutManager = LinearLayoutManager(requireContext())
        adapter = toolList?.let { ToolAdapter(requireContext(), it) }!!
        binding.recyclerToolac.adapter = adapter
        gettool()
        binding.addToolac.setOnClickListener {
            startActivity(Intent( requireContext(), AddToolActivity::class.java))
        }
        val ref = FirebaseDatabase.getInstance().reference.child("Admin")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (snap in snapshot.children){
                        val data = snap.getValue(Admin::class.java)
                        if (data != null) {
                            if (uid  == data.AdminId){
                                binding.addToolac.visibility = View.VISIBLE
                            }

                        }
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        return binding.root
    }


    private fun gettool() {
        val reff = FirebaseDatabase.getInstance().reference.child("Tools").child("Refrigeration//AC")
        reff.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    toolList?.clear()
                    for (snap in snapshot.children){
                        val data = snap.getValue(Tool::class.java)
                        if (data != null) {
                            toolList?.add(data)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}