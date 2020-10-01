package com.example.mymovies.ui.detailfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.mymovies.R
import com.example.mymovies.adapters.DetailFragmentTabsAdapter
import com.example.mymovies.databinding.FragmentDetailBinding
import com.example.mymovies.utils.loadImageWithGlide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding

class DetailFragment : Fragment() {
    private lateinit var viewModel: DetailFragmentViewModel

    companion object {
        const val BUNDLE_MOVIE_KEY_AS_INT = "MOVIE_INT_KEY"
        private const val BUNDLE_MOVIE_POSTER_PATH = "MOVIE_POSTER_PATH"
        private const val BUNDLE_MOVIE_MOVIE_TITLE = "MOVIE_TITLE"

        fun setMovieBundle(movieId: Int, posterPath: String, movieTitle: String): Bundle {
            val bundle = Bundle()
            bundle.putString(BUNDLE_MOVIE_POSTER_PATH, posterPath)
            bundle.putString(BUNDLE_MOVIE_MOVIE_TITLE, movieTitle)
            bundle.putInt(BUNDLE_MOVIE_KEY_AS_INT, movieId)
            return bundle
        }
    }

    private lateinit var binding: FragmentDetailBinding
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(DetailFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.appbar.applySystemWindowInsetsToPadding(top = true)
        setupToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.detailFragmentTabLayout
        val viewPager = binding.detailFragmentViewPager
        val adapterTabsAdapter = DetailFragmentTabsAdapter(this)
        viewPager.adapter = adapterTabsAdapter
        getMovieIdFromFirstFragment(adapterTabsAdapter)
        setTabLayoutMediator(tabLayout, viewPager)
    }

    private fun setTabLayoutMediator(tabLayout: TabLayout, viewPager: ViewPager2) {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Описание"
                1 -> tab.text = "Трейлеры"
            }
        }.attach()
    }


    private fun getMovieIdFromFirstFragment(adapter: DetailFragmentTabsAdapter) {
        arguments?.let { bundle ->
            bundle.getInt(BUNDLE_MOVIE_KEY_AS_INT).let {
                adapter.movieId = it
            }
            bundle.getString(BUNDLE_MOVIE_POSTER_PATH).let {
                it?.let { binding.ivPoster.loadImageWithGlide(it) }
            }
        }
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true).let { handleCollapsedToolbarTitle() }
    }

    private fun handleCollapsedToolbarTitle() {
        binding.appbar.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                // verify if the toolbar is completely collapsed and set the movie name as the title
                if (scrollRange + verticalOffset == 0) {
                    val title = arguments?.getString(BUNDLE_MOVIE_MOVIE_TITLE)
                    binding.collapsingToolbar.title = title
                            ?: resources.getString(R.string.app_name)
                    isShow = true
                } else if (isShow) {
                    // display an empty string when toolbar is expanded
                    binding.collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }

}

