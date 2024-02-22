package com.mksoluation.ttcskillbook.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mksoluation.ttcskillbook.*
import com.mksoluation.ttcskillbook.Models.Admin
import com.mksoluation.ttcskillbook.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        auth= FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        binding.imageCar.setOnClickListener {
            startActivity(Intent(requireContext(), DrivingActivity::class.java))
        }
        val ref = FirebaseDatabase.getInstance().reference.child("Admin")
        ref.addValueEventListener(object :ValueEventListener{
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
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
        binding.plumbingCard.setOnClickListener {
            startActivity(Intent(requireContext(), PlumbingActivity::class.java))
        }
        binding.imageWel.setOnClickListener {
            startActivity(Intent(requireContext(),WeldingActivity::class.java))
        }
        binding.imagepac.setOnClickListener {
            startActivity(Intent(requireContext(),RefrigerationActivity::class.java))
        }
        binding.imagger.setOnClickListener {
            startActivity(Intent(requireContext(),GarmentsActivity::class.java))
        }
        binding.imagecon.setOnClickListener {
            startActivity(Intent(requireContext(),ConstructionActivity::class.java))
        }
        binding.cardlan.setOnClickListener {
            startActivity(Intent(requireContext(),LanguageActivity::class.java))
        }

        return binding.root
    }



}