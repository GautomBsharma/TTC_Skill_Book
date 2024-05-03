package com.mksoluation.ttcskillbook

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mksoluation.ttcskillbook.Fragments.HomeFragment
import com.mksoluation.ttcskillbook.Fragments.NotificationsFragment
import com.mksoluation.ttcskillbook.Models.Admin
import com.mksoluation.ttcskillbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        binding.imageCar.setOnClickListener {
            startActivity(Intent(this, DrivingActivity::class.java))
        }
        val ref = FirebaseDatabase.getInstance().reference.child("Admin")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (snap in snapshot.children){
                        val data = snap.getValue(Admin::class.java)
                        if (data != null) {
                            if (uid  == data.AdminId){
                                binding.tvAdmin.visibility = View.VISIBLE
                            }

                        }
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        binding.tvAdmin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.plumbingCard.setOnClickListener {
            startActivity(Intent(this, PlumbingActivity::class.java))
        }
        binding.imageWel.setOnClickListener {
            startActivity(Intent(this,WeldingActivity::class.java))
        }
        binding.imagepac.setOnClickListener {
            startActivity(Intent(this,RefrigerationActivity::class.java))
        }
        binding.imagger.setOnClickListener {
            startActivity(Intent(this,GarmentsActivity::class.java))
        }
        binding.imagecon.setOnClickListener {
            startActivity(Intent(this,ConstructionActivity::class.java))
        }
        binding.cardlan.setOnClickListener {
            startActivity(Intent(this,LanguageActivity::class.java))
        }

    }
}