package com.example.mymovies.ui.detailfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mymovies.R
import com.example.mymovies.databinding.DetailFragmentBinding
import com.example.mymovies.entries.discover.movie.Result
import com.example.mymovies.ui.firstfragment.FirstFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.chrisbanes.insetter.applySystemWindowInsetsToMargin
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = FirstFragment()
    }

    private lateinit var binding: DetailFragmentBinding
    private val TAG = javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
/*
        binding.ivBigPoster.applySystemWindowInsetsToMargin(top = true)
*/

        var s: String? = null
        val type = object : TypeToken<Result>() {}.type
        val gson = Gson()
        val result: Result
        arguments?.let { bundle ->
            bundle.getString("FILM")?.let { s = it }
        }
        if (s != null) {
            result = gson.fromJson(s, type)
            binding.detailFragment = result
            Log.d(TAG, result.backdropPath)
        }
    }
}