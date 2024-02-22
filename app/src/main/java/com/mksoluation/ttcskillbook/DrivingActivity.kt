package com.mksoluation.ttcskillbook

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.mksoluation.ttcskillbook.Adapters.ViewPagerAdapter
import com.mksoluation.ttcskillbook.databinding.ActivityDrivingBinding

class DrivingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrivingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrivingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.TRANSPARENT
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Traffic Signals"
                1 -> tab.text = "Study Material"
            }
        }.attach()

    }
}