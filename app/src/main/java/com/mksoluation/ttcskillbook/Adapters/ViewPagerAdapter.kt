package com.mksoluation.ttcskillbook.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mksoluation.ttcskillbook.Fragments.LearnCarFragment
import com.mksoluation.ttcskillbook.Fragments.SignalFragment

class ViewPagerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SignalFragment()
            1 -> LearnCarFragment()

            else -> SignalFragment()
        }
    }
}