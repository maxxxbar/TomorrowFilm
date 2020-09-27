package com.example.mymovies.ui.detailfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mymovies.R
import com.example.mymovies.databinding.DetailFragmentBinding
import com.example.mymovies.entries.discover.movie.Result
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding

class DetailFragment : Fragment() {
    private lateinit var result: Result

    companion object{
        const val BUNDLE_MOVIE_KEY = "MOVIE"
    }

    private lateinit var binding: DetailFragmentBinding
    private val TAG = javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        binding.appbar.applySystemWindowInsetsToPadding(top = true)
        setupToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgumentsFromFirstFragment()
    }

    private fun getArgumentsFromFirstFragment() {
        var s: String? = null
        val type = object : TypeToken<Result>() {}.type
        val gson = Gson()
        arguments?.let { bundle ->
            bundle.getString(BUNDLE_MOVIE_KEY)?.let { s = it }
        }.also {
            s?.let {
                result = gson.fromJson(s, type)
                binding.movie = result
                Log.d(TAG, result.backdropPath)
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
                    binding.collapsingToolbar.title = result.title
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

