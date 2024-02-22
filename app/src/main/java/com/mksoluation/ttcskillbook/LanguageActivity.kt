package com.mksoluation.ttcskillbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.mksoluation.ttcskillbook.Adapters.ViewPagerConsAdapter
import com.mksoluation.ttcskillbook.Adapters.ViewPagerLangAdapter
import com.mksoluation.ttcskillbook.databinding.ActivityLanguageBinding

class LanguageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLanguageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ViewPagerLangAdapter(supportFragmentManager, lifecycle)
        binding.viewPagerLan.adapter = adapter
        TabLayoutMediator(binding.tabLayoutlang, binding.viewPagerLan) { tab, position ->
            when (position) {
                0 -> tab.text = "Japani"
                1 -> tab.text = "Korean"
            }
        }.attach()
    }
}