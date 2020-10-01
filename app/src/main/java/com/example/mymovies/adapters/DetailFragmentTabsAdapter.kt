package com.example.mymovies.adapters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mymovies.etc.DemoObjectFragment
import com.example.mymovies.ui.DemoFragmentFirst
import com.example.mymovies.ui.DemoFragmentTwo
import com.example.mymovies.ui.detailfragment.DetailFragment.Companion.BUNDLE_MOVIE_KEY_AS_INT
import com.example.mymovies.ui.detailfragment.DetailFragmentDescription

class DetailFragmentTabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val TAG = javaClass.simpleName
    var movieId: Int? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val detailFragmentDescription = DetailFragmentDescription()
        movieId?.let {
            detailFragmentDescription.arguments = Bundle().apply {
                putInt(BUNDLE_MOVIE_KEY_AS_INT, it)
                Log.d(TAG, "createFragment: $it")
            }
        }
            return when (position) {
            0 -> detailFragmentDescription
            1 -> DemoFragmentTwo()
            else -> DemoObjectFragment()
        }
    }
}