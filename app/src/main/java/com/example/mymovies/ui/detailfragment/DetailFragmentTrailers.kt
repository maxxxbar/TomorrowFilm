package com.example.mymovies.ui.detailfragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R
import com.example.mymovies.adapters.TrailerAdapter
import com.example.mymovies.databinding.FragmentDetailTrailersBinding
import com.example.mymovies.entries.discover.trailer.Result
import com.example.mymovies.ui.detailfragment.DetailFragment.Companion.BUNDLE_MOVIE_KEY_AS_INT
import io.reactivex.rxjava3.kotlin.subscribeBy

class DetailFragmentTrailers : Fragment(R.layout.fragment_detail_trailers) {

    companion object {
        private const val YOUTUBE_MOVIE_URL = "https://www.youtube.com/watch?v="
    }

    private lateinit var viewModel: DetailFragmentViewModel
    private lateinit var binding: FragmentDetailTrailersBinding
    private lateinit var recyclerView: RecyclerView
    private var trailerAdapter = TrailerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(DetailFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailTrailersBinding.inflate(layoutInflater)
        getMovieFromTrailerAdapter()
        setRecyclerView()
        return binding.root
    }


    private fun getMovieFromTrailerAdapter() {
        arguments?.let { bundle ->
            bundle.getInt(BUNDLE_MOVIE_KEY_AS_INT).let { movieId ->
                viewModel.getTrailers(movieId).subscribeBy(
                        onSuccess = { list: List<Result> -> setAdapter(list) },
                        onError = { throwable -> throwable.stackTrace }
                )
            }
        }
    }

    private fun setAdapter(list: List<Result>) {
        trailerAdapter.setTrailerList(list)
        trailerAdapter.setOnClickWatchTrailer { position ->
            val movieUrl = YOUTUBE_MOVIE_URL + list[position].key
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movieUrl))
            startActivity(intent)
        }
    }

    private fun setRecyclerView() {
        recyclerView = binding.rvFragmentDetailTrailers
        recyclerView.adapter = trailerAdapter
    }
}