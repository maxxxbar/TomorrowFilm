package com.example.mymovies.ui.detailfragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mymovies.R
import com.example.mymovies.adapters.DetailFragmentTabsAdapter
import com.example.mymovies.databinding.DetailFragmentBinding
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.ui.mainactivity.MainActivity
import com.example.mymovies.utils.loadImageWithGlide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {
    private var result: DiscoverMovieResultsItem? = null
    private lateinit var viewmodel: DetailFragmentViewModel
    private var job: Job? = null

    companion object {
        const val BUNDLE_MOVIE_KEY_AS_STRING = "MOVIE_STRING_KEY"
        const val BUNDLE_MOVIE_KEY_AS_INT = "MOVIE_INT_KEY"
    }

    private lateinit var binding: DetailFragmentBinding
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(DetailFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        binding.appbar.applySystemWindowInsetsToPadding(top = true)
        setupToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getArgumentsFromFirstFragment()
        getMovieFromDatabase()
        val tabLayout = binding.detailFragmentTabLayout
        val viewPager = binding.detailFragmentViewPager
        viewPager.adapter = DetailFragmentTabsAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "TAB ${position + 1}"
        }.attach()
    }

    private fun getMovieFromDatabase() {
        job?.cancel()
        job = lifecycleScope.launch {
            var movieId: Int? = null
            Log.d(TAG, "getMovieFromDatabase: ${Thread.currentThread().name}")
            val bundle = arguments
            if (bundle != null) {
                movieId = bundle.getInt(BUNDLE_MOVIE_KEY_AS_INT)
            }
            if (movieId != null) {
                viewmodel.getMovieFromDatabase(movieId).collect {
                    result = it
                    binding.movie = result
                    it.posterPath?.let { binding.ivPoster.loadImageWithGlide(it) }
                }
            }
        }
    }


    private fun getArgumentsFromFirstFragment() {
        var value: String? = null
        val type = object : TypeToken<DiscoverMovieResultsItem>() {}.type
        val gson = Gson()
        arguments?.let { bundle ->
            bundle.getString(BUNDLE_MOVIE_KEY_AS_STRING)?.let {
                value = it
            }
        }
        if (value != null) {
            result = gson.fromJson(value, type)
            binding.movie = result
            result?.posterPath?.let { binding.ivPoster.loadImageWithGlide(it) }
        } else {
            startActivity(Intent(requireContext(), MainActivity::class.java))
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
                    binding.collapsingToolbar.title = result?.title
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

