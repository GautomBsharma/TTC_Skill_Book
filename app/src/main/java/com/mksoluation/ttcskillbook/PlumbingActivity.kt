package com.mksoluation.ttcskillbook

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.mksoluation.ttcskillbook.Adapters.ViewPagerPlumAdapter
import com.mksoluation.ttcskillbook.databinding.ActivityPlumbingBinding

class PlumbingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlumbingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlumbingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.TRANSPARENT
        val adapter = ViewPagerPlumAdapter(supportFragmentManager, lifecycle)
        binding.viewPagerp.adapter = adapter
        TabLayoutMediator(binding.tabLayoutp, binding.viewPagerp) { tab, position ->
            when (position) {
                0 -> tab.text = "Tools"
                1 -> tab.text = "Study Material"
            }
        }.attach()
    }
}