package com.mksoluation.ttcskillbook

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mksoluation.ttcskillbook.Adapters.LearnAdapter
import com.mksoluation.ttcskillbook.Models.Admin
import com.mksoluation.ttcskillbook.Models.Learn
import com.mksoluation.ttcskillbook.databinding.ActivityGarmentsBinding

class GarmentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGarmentsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: LearnAdapter
    private var learnList:ArrayList<Learn>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGarmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        learnList = ArrayList()
        binding.learnRecycleGar.layoutManager = LinearLayoutManager(this)
        adapter = learnList?.let { LearnAdapter(this, it) }!!
        binding.learnRecycleGar.adapter = adapter
        gettool()

        binding.addLearGar.setOnClickListener {
            startActivity(Intent( this, AddLearnActivity::class.java))
        }

        val ref = FirebaseDatabase.getInstance().reference.child("Admin")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (snap in snapshot.children){
                        val data = snap.getValue(Admin::class.java)
                        if (data != null) {
                            if (uid  == data.AdminId){
                                binding.addLearGar.visibility = View.VISIBLE
                            }

                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun gettool() {
        val reff = FirebaseDatabase.getInstance().reference.child("Learn").child("Garments")
        reff.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    learnList?.clear()
                    for (snap in snapshot.children){
                        val data = snap.getValue(Learn::class.java)
                        if (data != null) {
                            learnList?.add(data)
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