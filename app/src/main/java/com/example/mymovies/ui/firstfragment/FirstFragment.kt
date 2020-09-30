package com.example.mymovies.ui.firstfragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R
import com.example.mymovies.adapters.LoadStateAdapter
import com.example.mymovies.adapters.MovieAdapterNew
import com.example.mymovies.data.MovieRepository
import com.example.mymovies.databinding.FirstFragmentBinding
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.network.ConnectionAPI
import com.example.mymovies.ui.detailfragment.DetailFragment.Companion.BUNDLE_MOVIE_KEY
import com.example.mymovies.ui.firstfragmentLi.FirstFragmentViewModel
import com.example.mymovies.utils.findNavController
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi

class FirstFragment : Fragment() {

    private lateinit var viewModel: FirstFragmentViewModel
    private lateinit var binding: FirstFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private val TAG = javaClass.simpleName
    private val adapter = MovieAdapterNew()
    private var getMoviesJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(FirstFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.first_fragment, container, false)
        val flexboxLayoutManager = FlexboxLayoutManager(requireContext())
        recyclerView = binding.recyclerViewPosters
        setupFlexLayoutManager(flexboxLayoutManager)
        setupRecyclerView(flexboxLayoutManager)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        getMovies()
    }

    private fun getMovies() {
        getMoviesJob?.cancel()
        getMoviesJob = lifecycleScope.launch {
            viewModel.getMoviesAsLiveData().observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setupRecyclerView(flexboxLayoutManager: FlexboxLayoutManager) {
        recyclerView.adapter = adapter
        recyclerView.applySystemWindowInsetsToPadding(top = true)
        recyclerView = binding.recyclerViewPosters
        recyclerView.layoutManager = flexboxLayoutManager
    }

    private fun setupFlexLayoutManager(flexboxLayoutManager: FlexboxLayoutManager) {
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP
        flexboxLayoutManager.alignItems = AlignItems.STRETCH
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
    }

    private fun initAdapter() {
        binding.retryButton.setOnClickListener { adapter.retry() }
        adapter.setOnFilmClickListener {
            setFilmFromIntent(it)
        }
        recyclerView.adapter = adapter.withLoadStateFooter(
                footer = LoadStateAdapter { adapter.retry() }
        )
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
        }
    }

    private fun setFilmFromIntent(movie: DiscoverMovieResultsItem) {
        val bundle = Bundle()
        val gson = Gson()
        val value = gson.toJson(movie)
        bundle.putString(BUNDLE_MOVIE_KEY, value)
        findNavController().navigate(R.id.action_firstFragment_to_detailFragment, bundle)
    }

}


