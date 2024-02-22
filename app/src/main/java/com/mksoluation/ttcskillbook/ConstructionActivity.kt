package com.mksoluation.ttcskillbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.mksoluation.ttcskillbook.Adapters.ViewPagerAdapter
import com.mksoluation.ttcskillbook.Adapters.ViewPagerConsAdapter
import com.mksoluation.ttcskillbook.databinding.ActivityConstructionBinding

class ConstructionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityConstructionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConstructionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ViewPagerConsAdapter(supportFragmentManager, lifecycle)
        binding.viewPagerCon.adapter = adapter
        TabLayoutMediator(binding.tabLayoutCon, binding.viewPagerCon) { tab, position ->
            when (position) {
                0 -> tab.text = "Tools"
                1 -> tab.text = "Study Material"
            }
        }.attach()
    }
}