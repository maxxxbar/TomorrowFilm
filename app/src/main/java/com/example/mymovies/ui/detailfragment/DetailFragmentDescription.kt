package com.example.mymovies.ui.detailfragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mymovies.R
import com.example.mymovies.databinding.FragmentDetailDescriptionBinding
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.ui.mainactivity.MainActivity
import com.example.mymovies.utils.loadImageWithGlide
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailFragmentDescription : Fragment() {
    private lateinit var binding: FragmentDetailDescriptionBinding
    private var job: Job? = null
    private val TAG = javaClass.simpleName
    private lateinit var viewmodel: DetailFragmentViewModel
    private var result: DiscoverMovieResultsItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(DetailFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_description, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            getMovieFromDatabase(bundle.getInt(DetailFragment.BUNDLE_MOVIE_KEY_AS_INT))
        }
    }

    private fun getMovieFromDatabase(movieId: Int) {
        job?.cancel()
        job = lifecycleScope.launch {
            Log.d(TAG, "getMovieFromDatabase: ${Thread.currentThread().name}")
            viewmodel.getMovieFromDatabase(movieId).collect {
                result = it
                binding.movie = result
            }
        }
    }

}
