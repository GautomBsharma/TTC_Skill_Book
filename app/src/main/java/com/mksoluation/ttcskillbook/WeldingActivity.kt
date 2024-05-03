package com.mksoluation.ttcskillbook

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mksoluation.ttcskillbook.Adapters.ToolAdapter
import com.mksoluation.ttcskillbook.Adapters.ViewPagerPlumAdapter
import com.mksoluation.ttcskillbook.Adapters.ViewPagerWeldAdapter
import com.mksoluation.ttcskillbook.Models.Admin
import com.mksoluation.ttcskillbook.Models.Tool
import com.mksoluation.ttcskillbook.databinding.ActivityWeldingBinding

class WeldingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeldingBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: ToolAdapter
    private var toolList:ArrayList<Tool>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeldingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        toolList = ArrayList()
        binding.recyclerSignalwel.layoutManager = LinearLayoutManager(this)
        adapter = toolList?.let { ToolAdapter(this, it) }!!
        binding.recyclerSignalwel.adapter = adapter
        binding.recyclerSignalwel.setHasFixedSize(true)
        //gettool()
        binding.addTool.setOnClickListener {
            startActivity(Intent( this, AddToolActivity::class.java))
        }
        if (isNetworkAvailable(this)) {
            // Internet is available, retrieve data
            gettool()
        } else {
            // No internet connection, show dialog
            showNoInternetDialog()
        }
        val ref = FirebaseDatabase.getInstance().reference.child("Admin")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (snap in snapshot.children){
                        val data = snap.getValue(Admin::class.java)
                        if (data != null) {
                            if (uid  == data.AdminId){
                                binding.addTool.visibility = View.VISIBLE
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
        val reff = FirebaseDatabase.getInstance().reference.child("Tools").child("Welding")
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
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }
    private fun showNoInternetDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("No Internet Connection")
            .setMessage("Please check your internet connection and try again.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }
}