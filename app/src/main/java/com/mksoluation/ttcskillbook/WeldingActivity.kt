package com.mksoluation.ttcskillbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.mksoluation.ttcskillbook.Adapters.ViewPagerPlumAdapter
import com.mksoluation.ttcskillbook.Adapters.ViewPagerWeldAdapter
import com.mksoluation.ttcskillbook.databinding.ActivityWeldingBinding

class WeldingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeldingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeldingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ViewPagerWeldAdapter(supportFragmentManager, lifecycle)
        binding.viewPagerp.adapter = adapter
        TabLayoutMediator(binding.tabLayoutp, binding.viewPagerp) { tab, position ->
            when (position) {
                0 -> tab.text = "Tools"
                1 -> tab.text = "Study Material"
            }
        }.attach()
    }
}