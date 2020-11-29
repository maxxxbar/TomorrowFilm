package com.example.mymovies.ui.firstfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R
import com.example.mymovies.adapters.LoadStateAdapter
import com.example.mymovies.adapters.MovieAdapter
import com.example.mymovies.databinding.FirstFragmentBinding
import com.example.mymovies.utils.findNavController
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class FirstFragment : Fragment(R.layout.first_fragment) {

    private lateinit var viewModel: FirstFragmentViewModel
    private lateinit var binding: FirstFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var flexBoxLayoutManager: FlexboxLayoutManager

    private val TAG = javaClass.simpleName
    private val movieAdapter = MovieAdapter()
    private var getMoviesJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(FirstFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FirstFragmentBinding.inflate(layoutInflater)
        setupFlexLayoutManager()
        setupRecyclerView()
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
                movieAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewPosters.apply {
            adapter = movieAdapter
            applySystemWindowInsetsToPadding(top = true)
            layoutManager = flexBoxLayoutManager
        }
    }

    private fun setupFlexLayoutManager() {
        flexBoxLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
            flexDirection = FlexDirection.ROW
        }
    }

    private fun initAdapter() {
        binding.retryButton.setOnClickListener { movieAdapter.retry() }
        movieAdapter.setOnFilmClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_detailFragment, viewModel.setFilmForDetailFragment(it))
        }
        recyclerView.adapter = movieAdapter.withLoadStateFooter(
                footer = LoadStateAdapter { movieAdapter.retry() }
        )
        movieAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        movieAdapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
        }
    }

}


