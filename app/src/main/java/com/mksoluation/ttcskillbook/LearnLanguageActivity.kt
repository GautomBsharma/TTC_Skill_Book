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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mksoluation.ttcskillbook.Adapters.LanguageAdapter
import com.mksoluation.ttcskillbook.Adapters.LearnAdapter
import com.mksoluation.ttcskillbook.Models.Admin
import com.mksoluation.ttcskillbook.Models.Japani
import com.mksoluation.ttcskillbook.Models.Learn
import com.mksoluation.ttcskillbook.Models.Tool
import com.mksoluation.ttcskillbook.databinding.ActivityLearnLanguageBinding

class LearnLanguageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLearnLanguageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: LanguageAdapter
    private var learnLanguaList: ArrayList<Learn>?=null
    var moduleName :String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnLanguageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        MobileAds.initialize(this) {}
        val uid = auth.currentUser?.uid
        moduleName = intent.getStringExtra("MODULE_NAME").toString()
        learnLanguaList = ArrayList()
        binding.learnlangrecye.layoutManager = LinearLayoutManager(this)
        adapter = learnLanguaList?.let { LanguageAdapter(this, it) }!!
        binding.learnlangrecye.adapter = adapter
        binding.learnlangrecye.setHasFixedSize(true)
        if (isNetworkAvailable(this)) {
            // Internet is available, retrieve data
            gettool()
        } else {
            // No internet connection, show dialog
            showNoInternetDialog()
        }
        binding.addLearnJapani.setOnClickListener {
            val intent = Intent(this,AddLearnActivity::class.java)

            startActivity(intent)
        }
        val adRequest = AdRequest.Builder().build()
        binding.adView1.loadAd(adRequest)

        val ref = FirebaseDatabase.getInstance().reference.child("Admin")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (snap in snapshot.children){
                        val data = snap.getValue(Admin::class.java)
                        if (data != null) {
                            if (uid  == data.AdminId){
                                binding.addLearnJapani.visibility = View.VISIBLE
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
        val reff = FirebaseDatabase.getInstance().reference.child("Learn").child(moduleName)
        reff.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    learnLanguaList?.clear()
                    for (snap in snapshot.children){
                        val data = snap.getValue(Learn::class.java)
                        if (data != null) {
                            learnLanguaList?.add(data)
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