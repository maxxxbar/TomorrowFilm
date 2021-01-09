package ws.worldshine.tomorrowfilm.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ws.worldshine.tomorrowfilm.ui.detailfragment.DetailFragment.Companion.BUNDLE_MOVIE_KEY_AS_INT
import ws.worldshine.tomorrowfilm.ui.detailfragment.DetailFragmentDescription
import ws.worldshine.tomorrowfilm.ui.detailfragment.DetailFragmentTrailers
import ws.worldshine.tomorrowfilm.ui.placeholderfragment.PlaceholderFragment

class DetailFragmentTabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    var movieId: Int? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> createFragmentForDescription()
            1 -> createFragmentForTrailers()
            else -> PlaceholderFragment()
        }
    }

    private fun createFragmentForDescription(): Fragment {
        val detailFragmentDescription = DetailFragmentDescription()
        val bundle = Bundle()
        movieId?.let {
            bundle.putInt(BUNDLE_MOVIE_KEY_AS_INT, it)
            detailFragmentDescription.arguments = bundle
        }
        return detailFragmentDescription
    }

    private fun createFragmentForTrailers(): Fragment {
        val detailFragmentTrailers = DetailFragmentTrailers()
        val bundle = Bundle()
        movieId?.let {
            bundle.putInt(BUNDLE_MOVIE_KEY_AS_INT, it)
            detailFragmentTrailers.arguments = bundle
        }
        return detailFragmentTrailers
    }
}