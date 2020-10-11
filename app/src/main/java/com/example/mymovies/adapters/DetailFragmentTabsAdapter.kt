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
import com.example.mymovies.ui.detailfragment.DetailFragmentTrailers

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
            }
        }
        return when (position) {
            0 -> detailFragmentDescription
            1 -> createFragmentForTrailers()
            else -> DemoObjectFragment()
        }
    }

    private fun createFragmentForTrailers(): Fragment {
        val detailFragmentTrailers = DetailFragmentTrailers()
        val bundle = Bundle()
        movieId?.let {
            Log.d(TAG, "createFragmentForTrailers: $movieId")
            bundle.putInt(BUNDLE_MOVIE_KEY_AS_INT, it)
            detailFragmentTrailers.arguments = bundle
        }
        return detailFragmentTrailers
    }
}