package com.mksoluation.ttcskillbook.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mksoluation.ttcskillbook.Adapters.SignalAdapter
import com.mksoluation.ttcskillbook.AddSignalActivity
import com.mksoluation.ttcskillbook.Models.Admin
import com.mksoluation.ttcskillbook.Models.Signal
import com.mksoluation.ttcskillbook.databinding.FragmentSignalBinding


class SignalFragment : Fragment() {

    private lateinit var binding: FragmentSignalBinding
    private lateinit var adapter: SignalAdapter
    private var signalList:ArrayList<Signal> ?=null
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignalBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        MobileAds.initialize(requireContext()) {}
        signalList = ArrayList()
        adapter = SignalAdapter(requireContext(), signalList!!)
        binding.recyclerSignal.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSignal.adapter = adapter
        binding.recyclerSignal.setHasFixedSize(true)
        if (isNetworkAvailable(requireContext())) {
            // Internet is available, retrieve data
            getSignal()
        } else {
            // No internet connection, show dialog
            showNoInternetDialog()
        }
        binding.addSignal.setOnClickListener {
            startActivity(Intent(requireContext(),AddSignalActivity::class.java))
        }
        val adRequest = AdRequest.Builder().build()
        binding.adViewttcBanner2.loadAd(adRequest)


        getAdmin(uid)


        return binding.root
    }

    private fun getAdmin(uid: String?) {


        val ref = FirebaseDatabase.getInstance().reference.child("Admin")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (snap in snapshot.children){
                        val data = snap.getValue(Admin::class.java)
                        if (data != null) {
                            if (uid  == data.AdminId){
                                binding.addSignal.visibility = View.VISIBLE
                            }

                        }
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun getSignal() {
        val ref = FirebaseDatabase.getInstance().reference.child("Signal")
        ref.addValueEventListener(object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    signalList?.clear()
                    for (snap in snapshot.children){
                        val data = snap.getValue(Signal::class.java)
                        if (data != null) {
                            signalList?.add(data)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    /*private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities =
                connectivityManager.activeNetwork ?: return false

            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }*/
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
        val dialog = AlertDialog.Builder(requireContext())
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