package com.example.mymovies.ui.firstfragment

import android.os.Bundle
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
import com.example.mymovies.databinding.FirstFragmentBinding
import com.example.mymovies.ui.detailfragment.DetailFragment
import com.example.mymovies.ui.firstfragmentLi.FirstFragmentViewModel
import com.example.mymovies.utils.findNavController
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.Job
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
            if (it.title != null && it.posterPath != null) {
                setFilmFromIntent(
                        movieId = it.id,
                        posterPath = it.posterPath,
                        movieTitle = it.title
                )
            }
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

    private fun setFilmFromIntent(movieId: Int, posterPath: String, movieTitle: String) {
        val bundle = DetailFragment.setMovieBundle(
                movieId = movieId,
                posterPath = posterPath,
                movieTitle = movieTitle)
        findNavController().navigate(R.id.action_firstFragment_to_detailFragment, bundle)
    }

}


