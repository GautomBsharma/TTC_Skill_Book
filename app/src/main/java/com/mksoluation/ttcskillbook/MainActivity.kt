package com.mksoluation.ttcskillbook

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mksoluation.ttcskillbook.Fragments.HomeFragment
import com.mksoluation.ttcskillbook.Fragments.NotificationsFragment
import com.mksoluation.ttcskillbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.TRANSPARENT

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.notifi -> replaceFragment(NotificationsFragment())

                else ->{
                }
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finishAffinity()
        } else {
            super.onBackPressed()
        }
    }
}