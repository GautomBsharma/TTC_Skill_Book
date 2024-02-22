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
import com.mksoluation.ttcskillbook.Adapters.NotificationAdapter
import com.mksoluation.ttcskillbook.AddNotificationActivity
import com.mksoluation.ttcskillbook.Models.Admin
import com.mksoluation.ttcskillbook.Models.Learn
import com.mksoluation.ttcskillbook.Models.Notification
import com.mksoluation.ttcskillbook.databinding.FragmentNotificationsBinding


class NotificationsFragment : Fragment() {
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: NotificationAdapter
    private lateinit var notificationList: ArrayList<Notification>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        binding.recycleNoti.layoutManager = LinearLayoutManager(requireContext())
        notificationList = ArrayList()
        adapter = NotificationAdapter(requireContext(),notificationList)
        getNotification()
        binding.addNotifications.setOnClickListener {
            startActivity(Intent( requireContext(), AddNotificationActivity::class.java))
        }

        val ref = FirebaseDatabase.getInstance().reference.child("Admin")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (snap in snapshot.children){
                        val data = snap.getValue(Admin::class.java)
                        if (data != null) {
                            if (uid  == data.AdminId){
                                binding.addNotifications.visibility = View.VISIBLE
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

    private fun getNotification() {
        val dbRef = FirebaseDatabase.getInstance().reference.child("Notifications")
        dbRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    notificationList?.clear()
                    for (snap in snapshot.children){
                        val data = snap.getValue(Notification::class.java)
                        if (data != null) {
                            notificationList?.add(data)
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