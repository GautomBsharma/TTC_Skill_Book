package com.mksoluation.ttcskillbook

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.mksoluation.ttcskillbook.Adapters.ViewPagerAcAdapter
import com.mksoluation.ttcskillbook.Adapters.ViewPagerPlumAdapter
import com.mksoluation.ttcskillbook.databinding.ActivityRefrigerationBinding

class RefrigerationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRefrigerationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRefrigerationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.TRANSPARENT
        val adapter = ViewPagerAcAdapter(supportFragmentManager, lifecycle)
        binding.viewPagerac.adapter = adapter
        TabLayoutMediator(binding.tabLayoutac, binding.viewPagerac) { tab, position ->
            when (position) {
                0 -> tab.text = "Tools"
                1 -> tab.text = "Study Material"
            }
        }.attach()
    }
}