package com.example.mymovies.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mymovies.etc.DemoObjectFragment
import com.example.mymovies.ui.DemoFragmentFirst
import com.example.mymovies.ui.DemoFragmentTwo

class DetailFragmentTabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DemoFragmentFirst()
            1 -> DemoFragmentTwo()
            else -> DemoObjectFragment()
        }
    }
}